package com.pix.testdialogfragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_custom_view_dialog.*

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 21-2-15
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
class CustomViewDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(requireContext())
        // 设置主题的构造方法
//        var builder = AlertDialog.Builder(activity, R.style.CustomDialog)
        var inflater = activity?.layoutInflater
        var view = inflater?.inflate(R.layout.layout_custom_view_dialog, null)
        builder.setView(view)
        view?.findViewById<AppCompatButton>(R.id.btn_close)?.setOnClickListener { dismiss() }
        return builder.create()
        // 另一种写法
//        var inflater = activity?.layoutInflater
//        var view = inflater?.inflate(R.layout.layout_custom_view_dialog, null)
//        var dialog = Dialog(requireContext())
//        // 设置主题的构造方法
////    var dialog = Dialog(requireContext(), R.style.CustomDialog)
//        dialog.setContentView(view);
//        // Do Someting
//        return dialog
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
    }
}