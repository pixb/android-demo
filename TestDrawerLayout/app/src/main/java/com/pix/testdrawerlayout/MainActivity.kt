package com.pix.testdrawerlayout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_simple.setOnClickListener(this)
        btn_toolbar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            btn_simple -> {
                startActivity(Intent(this,SimpleDrawerLayoutActivity::class.java))
            }
            btn_toolbar -> {
                startActivity(Intent(this,ToolbarDrawerActivity::class.java))
            }
        }
    }
}
