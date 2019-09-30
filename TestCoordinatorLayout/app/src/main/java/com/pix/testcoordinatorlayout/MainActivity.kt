package com.pix.testcoordinatorlayout

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> Snackbar.make(v, "FAB", Snackbar.LENGTH_LONG)
                    .setAction("cancel") {
                        //这里的单击事件代表点击消除Action后的响应事件
                    }.show()
            R.id.btn_test_behavior -> startActivity(Intent(this,TestBehaviorActivity::class.java))
            R.id.btn_test_rcv -> startActivity(Intent(this,TestRecyclerView::class.java))
        }
    }
}
