package com.pix.testdrawerlayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_toolbar_drawer.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class ToolbarDrawerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_drawer)
        toolbar.setLogo(R.mipmap.ic_launcher)
        toolbar.title = "ToolbarDrawerLayout"
        toolbar.subtitle = "Toolbar与侧滑抽屉结合Demo"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dl_drawer.addDrawerListener(ActionBarDrawerToggle(this,dl_drawer,toolbar,R.string.app_name, R.string.app_name))
    }
}
