package com.pix.testdialogfragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 21-2-15
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
class TestDialogFragment: DialogFragment() {
    private var isAnimation = false //用来判断是否多次点击。防止多次执行
    private var rootView: View? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 另一种写法
        var inflater = activity?.layoutInflater
        rootView = inflater?.inflate(R.layout.layout_test_dialog_fragment, null)
        var dialog = Dialog(requireContext())
        // 方法一: 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // 设置主题的构造方法
////    var dialog = Dialog(requireContext(), R.style.CustomDialog)
        rootView?.let { dialog.setContentView(it) }
//        // Do Someting
        rootView?.findViewById<AppCompatButton>(R.id.btn_close)?.setOnClickListener { animDismiss() }
        dialog.setCanceledOnTouchOutside(true)

        // ======================设置全屏=================================
        // 设置宽度为屏宽、位置靠近屏幕底部
        var window = dialog.window
        //设置了窗口的背景色为透明，这一步是必须的
        // <color name="transparent">#50000000</color>
        window?.setBackgroundDrawableResource(R.color.transparent)
        var wlp = window?.attributes
        wlp?.gravity = Gravity.BOTTOM;
        //设置窗口的宽度为 MATCH_PARENT,效果是和屏幕宽度一样大
        wlp?.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp?.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = wlp
//        =======================设置全屏二================================
        // 设置宽度为屏宽、靠近屏幕底部。
//        var window = getDialog().getWindow()
     //这步是必须的
//        window.setBackgroundDrawableResource(R.color.transparent)
     //必要，设置 padding，这一步也是必须的，内容不能填充全部宽度和高度
//        window?.decorView?.setPadding(0, 0, 0, 0)
//        var wlp = window?.attributes
//        wlp?.gravity = Gravity.BOTTOM
//        wlp?.width = WindowManager.LayoutParams.MATCH_PARENT
//        wlp?.height = WindowManager.LayoutParams.WRAP_CONTENT
//        window?.attributes = wlp
        rootView?.let { slideToUp(it) }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 方法二: 关闭标题栏，setContentView() 之前调用
//        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }
    // 设置从下到上弹出的动画
    fun slideToUp(animView : View) {
        var slide = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0F,
            Animation.RELATIVE_TO_SELF,
            0F,
            Animation.RELATIVE_TO_SELF,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0F
        )
        slide.run {
            duration = 400;
            isFillEnabled = true;
            fillAfter = true
        }
        animView.startAnimation(slide);
    }
    fun slideToDown(animView: View) {
        var slide = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f
        )
        slide.run {
            duration = 400;
            isFillEnabled = true;
            fillAfter = true
        }
        animView.startAnimation(slide);
        slide.setAnimationListener(object :Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {
                //用来判断是否多次点击。防止多次执行
                isAnimation = false;
                //弹框消失
                dismiss()
            }
            override fun onAnimationStart(p0: Animation?) {}
        })
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    fun animDismiss() {
        Log.d(TestDialogFragment::class.java.simpleName,  "dismiss: $rootView")
        if (isAnimation) true
        isAnimation = true
        rootView?.let { slideToDown(it) }
    }
}