package com.pix.testdialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment

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
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 另一种写法
        var inflater = activity?.layoutInflater
        var view = inflater?.inflate(R.layout.layout_test_dialog_fragment, null)
        var dialog = Dialog(requireContext())
        // 方法一: 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // 设置主题的构造方法
////    var dialog = Dialog(requireContext(), R.style.CustomDialog)
        view?.let { dialog.setContentView(it) }
//        // Do Someting
        view?.findViewById<AppCompatButton>(R.id.btn_close)?.setOnClickListener { dismiss() }
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
//        =================================================================
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 方法二: 关闭标题栏，setContentView() 之前调用
//        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }
}