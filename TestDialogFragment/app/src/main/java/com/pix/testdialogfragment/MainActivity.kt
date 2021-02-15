package com.pix.testdialogfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var simpleDialogFragment: SimpleDialogFragment? = null
    var customViewDialogFragment: CustomViewDialogFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        simpleDialogFragment = SimpleDialogFragment()
        btn_simple_dialog.setOnClickListener {
           simpleDialogFragment?.show(supportFragmentManager, "simple")
        }
        customViewDialogFragment = CustomViewDialogFragment()
        btn_custom_view_dialog.setOnClickListener {
            customViewDialogFragment?.show(supportFragmentManager, "custom view dialog")
        }
    }

}