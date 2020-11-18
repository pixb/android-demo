package com.pix.testglide

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val imgUrl = "http://pixtang.com:8001/img/201812061333063264.png"
    val localUrl = "/storage/emulated/0/guagua/QiQi/chat/751046800dd149e4a6df96adcebd7a46.jpeg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Glide.with(iv_img.context).load(imgUrl).into(iv_img)
        Glide.with(this).load(Uri.parse("file://$localUrl")).into(iv_img)
    }
}