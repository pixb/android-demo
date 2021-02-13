package com.pix.testglide

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val imgUrl = "http://pixtang.com:8001/img/201812061333063264.png"
    val localUrl = "/storage/emulated/0/guagua/QiQi/chat/751046800dd149e4a6df96adcebd7a46.jpeg"
    var TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Glide.with(iv_img.context).load(imgUrl).into(iv_img)
        thread {
            var base64 = ImageUtilsKt.fileToBase64String(File(localUrl))
            Log.d(TAG, "base64:$base64")
            var imageByteArray = Base64.decode(base64, Base64.DEFAULT)
            iv_img.ktxRunOnUi { Glide.with(this@MainActivity).load(imageByteArray).asBitmap().into(this) }
        }
//        Glide.with(this).load(Uri.parse("file://$localUrl")).into(iv_img)
    }
}