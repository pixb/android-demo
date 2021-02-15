package com.pix.testdialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
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
class SimpleDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 设置主题的构造方法
//        var builder = AlertDialog.Builder(activity, R.style.CustomDialog)
        var builder = AlertDialog.Builder(activity)
        builder.run {
            setTitle("SimpleDialogFragment")
            setMessage("是否退出？")
            setPositiveButton("确定", null)
            setNegativeButton("取消", null)
            setCancelable(false)
        }
        return builder.create()
    }
}