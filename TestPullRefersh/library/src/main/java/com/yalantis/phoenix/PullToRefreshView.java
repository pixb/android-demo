package com.yalantis.phoenix;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.yalantis.phoenix.refresh_view.BaseRefreshView;
import com.yalantis.phoenix.refresh_view.SunRefreshView;
import com.yalantis.phoenix.util.Utils;

import java.security.InvalidParameterException;

public class PullToRefreshView extends ViewGroup {

    //总的下拉距离120dp
    private static final int DRAG_MAX_DISTANCE = 120;
    private static final float DRAG_RATE = .5f;
    //先快后慢差值器值
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;

    public static final int STYLE_SUN = 0;
    public static final int MAX_OFFSET_ANIMATION_DURATION = 700;

    private static final int INVALID_POINTER = -1;

    private View mTarget;
    //刷新时显示的View
    private ImageView mRefreshView;
    //先快后慢差值器
    private Interpolator mDecelerateInterpolator;
    //触摸移动最小距离
    private int mTouchSlop;
    private int mTotalDragDistance;
    private BaseRefreshView mBaseRefreshView;
    private float mCurrentDragPercent;
    private int mCurrentOffsetTop;
    private boolean mRefreshing;
    //触摸点id
    private int mActivePointerId;
    //是否开始拖拽
    private boolean mIsBeingDragged;
    //初始Y坐标
    private float mInitialMotionY;
    private int mFrom;
    private float mFromDragPercent;
    private boolean mNotify;
    private OnRefreshListener mListener;

    //内容View的上边距
    private int mTargetPaddingTop;
    //内容View的下边距
    private int mTargetPaddingBottom;
    //内容View的右边距
    private int mTargetPaddingRight;
    //内容View的左边距
    private int mTargetPaddingLeft;

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //取得下拉刷新样式属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshView);
        final int type = a.getInteger(R.styleable.RefreshView_type, STYLE_SUN);
        a.recycle();
        /**
         * Constructor
         *
         * @param factor Degree to which the animation should be eased. 动画缓解的程度。
         *               Setting factor to 1.0f produces an upside-down y=x^2 parabola.
         *               设置因子为1.0f产生一个抛物线
         *               Increasing factor above 1.0f makes exaggerates the
         *        ease-out effect (i.e., it starts even faster and ends evens slower)
         *               上升的因子设置大约1.0f
         *               (它启动速度很快，结束很慢)
         */
        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        /**
         * getScaledTouchSlop是一个距离，表示滑动的时候，手的移动要大于这个距离才开始移动控件。
         * 如果小于这个距离就不触发移动控件，如viewpager就是用这个距离来判断用户是否翻页
         */
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //将下拉距离从dp转为像素
        mTotalDragDistance = Utils.convertDpToPixel(context, DRAG_MAX_DISTANCE);
        mRefreshView = new ImageView(context);
        //设置下拉刷新View的样式
        setRefreshStyle(type);
        //加入下拉刷新的View
        addView(mRefreshView);
        /**
        * If this view doesn't do any drawing on its own, set this flag to
        * allow further optimizations.
        * 如果这个view不能自身重绘，设置这个标志为进一步优化
        * By default, this flag is not set on
        * View, but could be set on some View subclasses such as ViewGroup.
        * 默认情况下,这个标志没有设置到View上
        * 但是可以设置到一些View的子类上，如ViewGroup
        * Typically, if you override {@link #onDraw(android.graphics.Canvas)}
        * you should clear this flag.
         * 通常情况下，如果你Override了onDraw()方法，你应该清除这个标志
        *
        * @param willNotDraw whether or not this View draw on its own
        */
        setWillNotDraw(false);
        //设置子View在绘制时允许排序
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    }

    /**
     * 设置下拉刷新的样式
     * 自定义枚举属性
     * @param type
     */
    public void setRefreshStyle(int type) {
        setRefreshing(false);
        switch (type) {
            case STYLE_SUN:
                mBaseRefreshView = new SunRefreshView(getContext(), this);
                break;
            default:
                throw new InvalidParameterException("Type does not exist");
        }
        mRefreshView.setImageDrawable(mBaseRefreshView);
    }

    /**
     * This method sets padding for the refresh (progress) view.
     */
    public void setRefreshViewPadding(int left, int top, int right, int bottom) {
        mRefreshView.setPadding(left, top, right, bottom);
    }

    //取得总的滑动距离
    public int getTotalDragDistance() {
        return mTotalDragDistance;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ensureTarget();
        if (mTarget == null)
            return;
        //设置测量的宽高，去掉Padding值
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingRight() - getPaddingLeft(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);
        mTarget.measure(widthMeasureSpec, heightMeasureSpec);
        mRefreshView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 确保存在刷新View
     */
    private void ensureTarget() {
        if (mTarget != null)
            return;
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != mRefreshView) {
                    mTarget = child;
                    mTargetPaddingBottom = mTarget.getPaddingBottom();
                    mTargetPaddingLeft = mTarget.getPaddingLeft();
                    mTargetPaddingRight = mTarget.getPaddingRight();
                    mTargetPaddingTop = mTarget.getPaddingTop();
                }
            }
        }
    }
    //事件拦截的处理
    @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!isEnabled() || canChildScrollUp() /*当子View向上滚动了，就不拦截了*/
                || mRefreshing) {
            return false;
        }
        //取得动作标志
        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setTargetOffsetTop(0, true);
                //记录触控点id
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                //没有开始刷新
                mIsBeingDragged = false;
                //取得触摸点Y坐标
                final float initialMotionY = getMotionEventY(ev, mActivePointerId);
                if (initialMotionY == -1) {
                    return false;
                }
                mInitialMotionY = initialMotionY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) { //判断之前触摸点
                    return false;
                }
                //但前y坐标
                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                //当前 - 开始 = 移动距离
                final float yDiff = y - mInitialMotionY;
                if (yDiff > mTouchSlop && !mIsBeingDragged) {   //开始移动
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }
        //开始拖拽，则消费事件
        return mIsBeingDragged;
    }
    //事件处理
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {

        if (!mIsBeingDragged) { //没有开始拖拽直接返回
            return super.onTouchEvent(ev);
        }
        //取得事件
        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                //取得当前y坐标
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                //取得移动距离
                final float yDiff = y - mInitialMotionY;
                //计算拖拽View需要移动距离
                final float scrollTop = yDiff * DRAG_RATE;
                mCurrentDragPercent = scrollTop / mTotalDragDistance;
                if (mCurrentDragPercent < 0) {
                    return false;
                }
                //算出拖拽百分比
                float boundedDragPercent = Math.min(1f, Math.abs(mCurrentDragPercent));
                //滑动补值
                float extraOS = Math.abs(scrollTop) - mTotalDragDistance;
                //拽下来的距离
                float slingshotDist = mTotalDragDistance;
                //拉动弹弓百分比
                float tensionSlingshotPercent = Math.max(0,
                        Math.min(extraOS, slingshotDist * 2) / slingshotDist);
                //
                float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
                        (tensionSlingshotPercent / 4), 2)) * 2f;
                float extraMove = (slingshotDist) * tensionPercent / 2;
                int targetY = (int) ((slingshotDist * boundedDragPercent) + extraMove);

                mBaseRefreshView.setPercent(mCurrentDragPercent, true);
                setTargetOffsetTop(targetY - mCurrentOffsetTop, true);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN:
                final int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overScrollTop = (y - mInitialMotionY) * DRAG_RATE;
                mIsBeingDragged = false;
                if (overScrollTop > mTotalDragDistance) {
                    setRefreshing(true, true);
                } else {
                    mRefreshing = false;
                    animateOffsetToStartPosition();
                }
                mActivePointerId = INVALID_POINTER;
                return false;
            }
        }

        return true;
    }

    private void animateOffsetToStartPosition() {
        mFrom = mCurrentOffsetTop;
        mFromDragPercent = mCurrentDragPercent;
        long animationDuration = Math.abs((long) (MAX_OFFSET_ANIMATION_DURATION * mFromDragPercent));

        mAnimateToStartPosition.reset();
        mAnimateToStartPosition.setDuration(animationDuration);
        mAnimateToStartPosition.setInterpolator(mDecelerateInterpolator);
        mAnimateToStartPosition.setAnimationListener(mToStartListener);
        mRefreshView.clearAnimation();
        mRefreshView.startAnimation(mAnimateToStartPosition);
    }

    private void animateOffsetToCorrectPosition() {
        mFrom = mCurrentOffsetTop;
        mFromDragPercent = mCurrentDragPercent;

        mAnimateToCorrectPosition.reset();
        mAnimateToCorrectPosition.setDuration(MAX_OFFSET_ANIMATION_DURATION);
        mAnimateToCorrectPosition.setInterpolator(mDecelerateInterpolator);
        mRefreshView.clearAnimation();
        mRefreshView.startAnimation(mAnimateToCorrectPosition);

        if (mRefreshing) {
            mBaseRefreshView.start();
            if (mNotify) {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        } else {
            mBaseRefreshView.stop();
            animateOffsetToStartPosition();
        }
        mCurrentOffsetTop = mTarget.getTop();
        mTarget.setPadding(mTargetPaddingLeft, mTargetPaddingTop, mTargetPaddingRight, mTotalDragDistance);
    }

    private final Animation mAnimateToStartPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            moveToStart(interpolatedTime);
        }
    };

    private final Animation mAnimateToCorrectPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int targetTop;
            int endTarget = mTotalDragDistance;
            targetTop = (mFrom + (int) ((endTarget - mFrom) * interpolatedTime));
            int offset = targetTop - mTarget.getTop();

            mCurrentDragPercent = mFromDragPercent - (mFromDragPercent - 1.0f) * interpolatedTime;
            mBaseRefreshView.setPercent(mCurrentDragPercent, false);

            setTargetOffsetTop(offset, false /* requires update */);
        }
    };

    private void moveToStart(float interpolatedTime) {
        int targetTop = mFrom - (int) (mFrom * interpolatedTime);
        float targetPercent = mFromDragPercent * (1.0f - interpolatedTime);
        int offset = targetTop - mTarget.getTop();

        mCurrentDragPercent = targetPercent;
        mBaseRefreshView.setPercent(mCurrentDragPercent, true);
        mTarget.setPadding(mTargetPaddingLeft, mTargetPaddingTop, mTargetPaddingRight, mTargetPaddingBottom + targetTop);
        setTargetOffsetTop(offset, false);
    }

    public void setRefreshing(boolean refreshing) {
        if (mRefreshing != refreshing) {
            setRefreshing(refreshing, false /* notify */);
        }
    }

    private void setRefreshing(boolean refreshing, final boolean notify) {
        if (mRefreshing != refreshing) {
            mNotify = notify;
            ensureTarget();
            mRefreshing = refreshing;
            if (mRefreshing) {
                mBaseRefreshView.setPercent(1f, true);
                animateOffsetToCorrectPosition();
            } else {
                animateOffsetToStartPosition();
            }
        }
    }

    private Animation.AnimationListener mToStartListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mBaseRefreshView.stop();
            mCurrentOffsetTop = mTarget.getTop();
        }
    };

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    /**
     *  设置content view 的位置
     * @param offset
     * @param requiresUpdate
     */
    private void setTargetOffsetTop(int offset, boolean requiresUpdate) {
        mTarget.offsetTopAndBottom(offset);
        mBaseRefreshView.offsetTopAndBottom(offset);
        mCurrentOffsetTop = mTarget.getTop();
        if (requiresUpdate && android.os.Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    /**
     * 判断子View检测是否可以向上滚动了,也就是可以向下滚动
     * @return
     */
    private boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) { //编译版本14以下
            if (mTarget instanceof AbsListView) { //是列表控件
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0 //条目数大于0
                        && (absListView.getFirstVisiblePosition() > 0/* 显示的第一个View不是列表第一个 */
                        || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
                            //第一个Item的最top点小于列表View的高点
            } else {
                return mTarget.getScrollY() > 0; //不是列表控件，判断滚动的Y轴是否大于0
            }
        } else {
            /**
             * Negative to check scrolling up, positive to check scrolling down.
             * 负数检测向上滚动，正数检测向下滚动
             */
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 检查是否可以上拉,对于版本14以下的暂不支持
     * @return
     */
    public boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, 1);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        ensureTarget();
        if (mTarget == null)
            return;

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        mTarget.layout(left, top + mCurrentOffsetTop, left + width - right, top + height - bottom + mCurrentOffsetTop);
        mRefreshView.layout(left, top, left + width - right, top + height - bottom);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

}

