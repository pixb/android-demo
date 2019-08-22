package com.guagua.live.lib.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.pix.pictextview.R;

import java.util.ArrayList;

/**
 * Created by 郁金涛 on 2016/7/22 14:47
 * 邮箱：jintao@17guagua.com
 * <p/>
 * 1.本版采用StaticLayout进行文字绘制，如果需要设置单独点击事件的话，那么就需要拦截触摸事件和分发了。
 * 2.没有支持文字的gravity和margin
 *
 * @description
 */
public class PicAndTextView extends ViewGroup {


    public static class LayoutParam extends ViewGroup.MarginLayoutParams {
        public int gravity;//Gravity.bottom,Gravity.top,Gravity.CENTER_VERTICAL

        public LayoutParam(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }
    }


    private TextPaint mPaint;
    private ArrayList<StaticLayoutEntry> mStaticLayoutEntries = new ArrayList<>();//所有的staticlayout
    private ArrayList<RectWapper> mEachLineRect = new ArrayList<>();//每行的rect
    private ArrayList<Object> mChildList = new ArrayList<>();
    private SparseArray<TextPaint> mPaintSparseArray = new SparseArray<>();//support spanString
    private int mWidth;//宽度
    private int mHeight;
    private RectWapper mCurrentLineRect;//current line rect
    private int mCurrentLine;//测量的当前行
    private int mLineOffset = 0;//行间距
    private int mMinLineHeight = 10;//最小行高
    private Rect temp = new Rect();//仅用于临时rect使用，不做数据保存
    private static int[] attrArr = new int[]{android.R.attr.textSize, android.R.attr.textColor, android.R.attr.textStyle, android.R.attr.fontFamily, android.R.attr.typeface};
    private static int TEXT_SIZE = 0;
    private static int TEXT_COLOR = 1;
    private static int TEXT_STYPE = 2;
    private static int TEXT_FONT_FAMILY = 3;
    private static int TEXT_TYPE_FACE = 4;

    public PicAndTextView(Context context) {
        super(context);
        init(context, null);
    }

    public PicAndTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressWarnings("ResourceType")
    private void init(Context context, AttributeSet attrs) {
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        int textColor = Color.BLACK;
        int textSize = 15;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, attrArr);
            textColor = typedArray.getColor(TEXT_COLOR, textColor);
            textSize = typedArray.getDimensionPixelSize(TEXT_SIZE, textSize);
            typedArray.recycle();
        }
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        //**************
//        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(2);
//        paint.setStyle(Paint.Style.STROKE);
    }

    public TextPaint getPaint() {
        return mPaint;
    }

    public void clean() {
        mChildList.clear();
        mHeight = 0;
        removeAllViews();
    }

    public void addNewChild(View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        addView(view);
        mChildList.add(view);
    }

    public void addNewChild(View view, LayoutParam layoutParam) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        addView(view, layoutParam);
        mChildList.add(view);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParam(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        if (p instanceof MarginLayoutParams || p instanceof LayoutParam) {
            return p;
        }
        return new LayoutParam(p.width, p.height, Gravity.BOTTOM);
    }

    public void addTextChild(CharSequence text) {
        mChildList.add(text);
    }

    public void addTextChild(CharSequence text, TextPaint textPaint) {
        mChildList.add(text);
        mPaintSparseArray.put(mChildList.size() - 1, textPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mStaticLayoutEntries.clear();
        mEachLineRect.clear();
        mWidth = getMeasuredWidth();
        mCurrentLineRect = null;
        mHeight = 0;
        mCurrentLine = 0;
        final int size = mChildList.size();
        for (int i = 0; i < size; i++) {
            Object object = mChildList.get(i);
            if (object instanceof View) {
                View view = (View) object;
                measureChildWithMargins(view, widthMeasureSpec, 0,heightMeasureSpec,0);
                measureChildView(view);
            } else if (object instanceof CharSequence) {
                CharSequence charSequence = (CharSequence) object;
                measureChildText(charSequence, mPaintSparseArray.get(i));
            }
        }
        mHeight = getPaddingBottom() + getPaddingBottom();
        for (int i = 0, length = mEachLineRect.size(); i < length; i++) {
            mHeight = mHeight + mEachLineRect.get(i).rect.height();
        }
        if (mEachLineRect.size() == 1) {
            mWidth = mEachLineRect.get(0).rect.width() - mEachLineRect.get(0).leaveWidth() + getPaddingRight() + getPaddingLeft();
        }
        mHeight = mHeight + mLineOffset * (Math.max(mEachLineRect.size() - 1, 0));
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
    }

    /**
     * @param charSequence
     */
    private void measureChildText(CharSequence charSequence, TextPaint textPaint) {
        int end = 0;
        do {
            end = splitCharSequence(charSequence, textPaint);
            if (end > 0) {
                charSequence = charSequence.subSequence(charSequence.length() - end, charSequence.length());
            }
        } while (end > 0);
    }


    private final int splitCharSequence(CharSequence charSequence, TextPaint textPaint) {
        StaticLayout staticLayout = null;
        if (textPaint == null) {
            textPaint = mPaint;
        }
        if (mCurrentLineRect != null && textPaint.getTextSize()-1 > mCurrentLineRect.leaveWidth()) {
            mCurrentLineRect.full = true;
        }
        if (mCurrentLineRect == null || mCurrentLineRect.full) {
            staticLayout = generateStaticLayout(charSequence, mWidth - getPaddingRight() - getPaddingLeft(), textPaint);
        } else {
            staticLayout = generateStaticLayout(charSequence, mCurrentLineRect.leaveWidth(), textPaint);
        }
        int end = staticLayout.getLineEnd(0);

        if (end < charSequence.length()) {
            if (mCurrentLineRect == null || mCurrentLineRect.full) {
                staticLayout = generateStaticLayout(charSequence.subSequence(0, end), mWidth - getPaddingRight() - getPaddingLeft(), textPaint);
            } else {
                staticLayout = generateStaticLayout(charSequence.subSequence(0, end), mCurrentLineRect.leaveWidth(), textPaint);
            }
        }
//        LogUtils.d("PicAndTextView","拆分字符："+charSequence.subSequence(0,end));
        addStaticLayoutEntry(staticLayout, mCurrentLineRect);
        return charSequence.length() - end;
    }


    /**
     * 此处的staticLayout
     *
     * @param staticLayout
     * @param currentWrapper
     */
    private final void addStaticLayoutEntry(StaticLayout staticLayout, RectWapper currentWrapper) {
        staticLayout.getLineBounds(0, temp);
        final int width = (int) staticLayout.getLineWidth(0);
        final int height = temp.height();
        if (currentWrapper == null || currentWrapper.full) {
            mCurrentLineRect = makeLineRect(caclueLineHeight(null, temp.height()), currentWrapper, mCurrentLine++);
            mEachLineRect.add(mCurrentLineRect);
        }
        //检查高度是否可用
        StaticLayoutEntry staticLayoutEntry = new StaticLayoutEntry(staticLayout, mCurrentLineRect.lineNumber);
        caclueLineHeight(mCurrentLineRect.rect, height);
        staticLayoutEntry.rect = new Rect(mCurrentLineRect.x, temp.top, mCurrentLineRect.x + width, height);
        mCurrentLineRect.addWidth(width);
        mStaticLayoutEntries.add(staticLayoutEntry);
    }


    private final void addViewEntry(View view, RectWapper currentWrapper) {
        final int height = view.getMeasuredHeight();
        final int width = view.getMeasuredWidth();
        final MarginLayoutParams marginLayoutParams= (MarginLayoutParams) view.getLayoutParams();
        final int realWidth=width+marginLayoutParams.leftMargin+marginLayoutParams.rightMargin;
        final int realheight=height+marginLayoutParams.bottomMargin+marginLayoutParams.topMargin;
        if (currentWrapper == null || currentWrapper.full || currentWrapper.leaveWidth() < realWidth) {
            mCurrentLineRect = makeLineRect(caclueLineHeight(null, realheight), currentWrapper, mCurrentLine++);
            mEachLineRect.add(mCurrentLineRect);
            view.getLayoutParams();
        }
        ViewLayoutEntry viewLayoutEntry = new ViewLayoutEntry(view, mCurrentLineRect.lineNumber);
        caclueLineHeight(mCurrentLineRect.rect, realheight);
        viewLayoutEntry.rect = new Rect(mCurrentLineRect.x, 0, mCurrentLineRect.x + width, height);
        mCurrentLineRect.addWidth(realWidth);
        view.setTag(R.id.li_pic_text_view_tag, viewLayoutEntry);

    }


    /**
     * 计算currentLine高度，如果但前存储的currentRect高度低于viewheight，则进行设置
     *
     * @param currentLineRect
     * @param viewHeight
     * @return
     */
    private int caclueLineHeight(Rect currentLineRect, int viewHeight) {
        if (currentLineRect == null) {
            return Math.max(viewHeight, mMinLineHeight);
        }
        int maxHeight = Math.max(currentLineRect.height(), viewHeight);
        currentLineRect.bottom = maxHeight + currentLineRect.top;
        return maxHeight;
    }


    private final void measureChildView(View view) {
        addViewEntry(view, mCurrentLineRect);
    }


    /**
     * 构造行的rectWarrper对象
     *
     * @param height
     * @param lastLineRect
     * @return
     */
    private final RectWapper makeLineRect(int height, RectWapper lastLineRect, int index) {
        Rect rect = new Rect();
        if (lastLineRect == null) {
            rect.top = getPaddingTop();
        } else {
            rect.top = lastLineRect.rect.bottom + mLineOffset;
        }
        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.bottom = rect.top + height;
        return new RectWapper(rect, index);
    }

    private StaticLayout generateStaticLayout(CharSequence mCharSequence, int width) {
        return new StaticLayout(mCharSequence, mPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }

    private StaticLayout generateStaticLayout(CharSequence mCharSequence, int width, TextPaint textPaint) {
        return new StaticLayout(mCharSequence, textPaint == null ? mPaint : textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        int all = canvas.save();
        for (StaticLayoutEntry staticLayoutEntry : mStaticLayoutEntries) {
            canvas.save();
            drawStaticLayout(canvas, staticLayoutEntry);
            canvas.restore();
        }
        canvas.restoreToCount(all);
        super.dispatchDraw(canvas);
    }

    private final void drawStaticLayout(Canvas canvas, StaticLayoutEntry staticLayoutEntry) {
        RectWapper rectWapper = mEachLineRect.get(staticLayoutEntry.line);
        final Rect rect = staticLayoutEntry.rect;
        canvas.translate(rectWapper.rect.left + rect.left, rectWapper.rect.top + rectWapper.rect.height() - rect.bottom);
        staticLayoutEntry.staticLayout.draw(canvas);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            ViewLayoutEntry viewLayoutEntry = (ViewLayoutEntry) view.getTag(R.id.li_pic_text_view_tag);
            if (viewLayoutEntry == null) return;
            final RectWapper rectWapper = mEachLineRect.get(viewLayoutEntry.line);
            layoutChild(view, (MarginLayoutParams) view.getLayoutParams(), rectWapper, viewLayoutEntry);
        }
    }


    /**
     * @param view
     * @param marginLayoutParams
     * @param lineRectWrapper
     * @param viewLayoutEntry
     */
    protected void layoutChild(View view, MarginLayoutParams marginLayoutParams, RectWapper lineRectWrapper, ViewLayoutEntry viewLayoutEntry) {
        int gravity = Gravity.BOTTOM;
        final Rect viewRect = viewLayoutEntry.rect;
        if (marginLayoutParams instanceof LayoutParam) {
            gravity = ((LayoutParam) marginLayoutParams).gravity;
        }
        int top;
        int bottom;
        int left;
        int right;
        switch (gravity) {
            case Gravity.TOP:
                top = lineRectWrapper.rect.top + marginLayoutParams.topMargin;
                bottom = top + viewLayoutEntry.rect.height();
                break;
            case Gravity.CENTER_VERTICAL:
            case Gravity.CENTER:
                top = lineRectWrapper.rect.top + (lineRectWrapper.rect.height() - viewLayoutEntry.rect.height()) / 2;
                bottom = top + viewLayoutEntry.rect.height();
                break;
            case Gravity.BOTTOM:
            default:
                top = lineRectWrapper.rect.bottom - viewLayoutEntry.rect.height() - marginLayoutParams.bottomMargin;
                bottom = top + viewLayoutEntry.rect.height();
                break;
        }
        left = viewLayoutEntry.rect.left + marginLayoutParams.leftMargin;
        right = left + viewLayoutEntry.rect.width();
        //此处必须要要加上lineRectWrapper.rect.left
        view.layout(left+lineRectWrapper.rect.left, top, right+lineRectWrapper.rect.left, bottom);
    }

    /**
     * 普通是视图的entry
     */
    private class ViewLayoutEntry {
        public View child;
        public int line;
        public Rect rect;

        public ViewLayoutEntry(View child, int line) {
            this.child = child;
            this.line = line;
        }


        @Override
        public String toString() {
            return "ViewLayoutEntry{" +
                    "child=" + child +
                    ", line=" + line +
                    ", rect=" + rect +
                    '}';
        }
    }

    public int getLineOffset() {
        return mLineOffset;
    }

    public void setLineOffset(int lineOffset) {
        this.mLineOffset = lineOffset;
    }


    /**
     * 文字的staticlayout控制类
     */
    private class StaticLayoutEntry {
        public StaticLayout staticLayout;
        public int line;//哪一行，从第0行开始
        public Rect rect;//相对于这一行的rect范围,使用的坐标系为以底部和行底部重叠为基准

        public StaticLayoutEntry(StaticLayout staticLayout, int line) {
            this.staticLayout = staticLayout;
            this.line = line;
        }


        @Override
        public String toString() {
            return "StaticLayoutEntry{" +
                    "rect=" + rect +
                    ", line=" + line +
                    ", staticLayout=" + staticLayout +
                    '}';
        }
    }


    /**
     * 工具类，rect封装对象
     */
    private class RectWapper {
        public Rect rect;//包含内容rect范围，已经减去了padding
        public int x;//相对于rect left的平移量
        public int y;//相对于rect top的平移量
        public boolean full;//是否满行
        public int lineNumber;//哪个行的
        public int paddingTop;
        public int paddingLeft;
        public int paddingRight;
        public int paddingBottom;

        public RectWapper(Rect rect, int lineNumber) {
            this.rect = rect;
            this.lineNumber = lineNumber;
            x = 0;
            y = 0;
        }

        public void addWidth(int addWidth) {
            x += addWidth;
            if (Math.round(x + 0.5) >= rect.width()) {
                full = true;
            }
        }

        public int leaveWidth() {
            return rect.width() - x;
        }

        @Override
        public String toString() {
            return "RectWapper{" +
                    "rect=" + rect +
                    ", x=" + x +
                    ", y=" + y +
                    ", full=" + full +
                    ", lineNumber=" + lineNumber +
                    '}';
        }
    }


}
