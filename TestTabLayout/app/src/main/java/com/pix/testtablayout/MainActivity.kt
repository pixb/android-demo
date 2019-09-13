package com.pix.testtablayout

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.pix.testtablayout.test.TestTabLayout01Activity
import com.pix.testtablayout.test.TestTabLayout02Activity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test01Click(v: View) {
        startActivity(Intent(this, TestTabLayout01Activity::class.java))
    }

    fun test02Click(v: View) {
        startActivity(Intent(this, TestTabLayout02Activity::class.java))
    }
}
