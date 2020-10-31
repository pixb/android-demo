package com.pix.testpopupwindow

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.PopupWindowCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var popView:View
    private lateinit var popWindow: PopupWindow
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popView = LayoutInflater.from(this).inflate(R.layout.layout_popup_test,null)
        popWindow = PopupWindow(popView,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popWindow.setBackgroundDrawable(ColorDrawable())
        btn_show.setOnClickListener {
//            popWindow.showAsDropDown(btn_show)
            if(popWindow.isShowing) popWindow.dismiss() else {
//                popWindow.showAtLocation(btn_show,Gravity.RIGHT, 0, 0)
                var btnLoc = IntArray(2)
                    btn_show.getLocationOnScreen(btnLoc)
                popWindow.showAtLocation(btn_show, Gravity.NO_GRAVITY, btnLoc[0] + btn_show.width / 2,btnLoc[1])
            }
        }
    }
    private fun makeDropDownMeasureSpec(measureSpec: Int): Int {
        val mode: Int = if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            View.MeasureSpec.UNSPECIFIED
        } else {
            View.MeasureSpec.EXACTLY
        }
        return View.MeasureSpec.makeMeasureSpec(
            View.MeasureSpec.getSize(
                measureSpec
            ), mode
        )
    }
}

