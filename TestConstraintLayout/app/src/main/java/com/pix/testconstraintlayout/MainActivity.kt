package com.pix.testconstraintlayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_relativepos.setOnClickListener(this)
        btn_bias.setOnClickListener(this)
        btn_ratio.setOnClickListener(this)
        btn_circularpos.setOnClickListener(this)
        btn_chains.setOnClickListener(this)
        btn_guide.setOnClickListener(this)
        btn_barrier.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when(view) {
            btn_relativepos -> startActivity(Intent(this,RelativePosActivity::class.java))
            btn_bias -> startActivity(Intent(this,BiasActivity::class.java))
            btn_ratio -> startActivity(Intent(this,RatioActivity::class.java))
            btn_chains -> startActivity(Intent(this,ChainsActivity::class.java))
            btn_circularpos -> startActivity(Intent(this,CircularPosActivity::class.java))
            btn_guide -> startActivity(Intent(this,GuideActivity::class.java))
            btn_barrier -> startActivity(Intent(this,BarrierActivity::class.java))
        }
    }
}
