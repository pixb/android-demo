package com.pix.testclipview

import android.graphics.Outline
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    /**
     * 轮廓提供者的使用步骤
     * 1. 自定义轮廓提供者，并且重写getOutline方法来提取轮廓
     * 2. 通过view.setClipToOutline(true)方法开启组建的裁剪功能
     * 3. 通过.setOutlineProvider(new MyViewOutlineProvider())方法设置自定义的轮廓提供者完成裁剪
     *
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_clip.setOnClickListener(this)
        v1.outlineProvider = CircleViewProvider()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    class CircleViewProvider : ViewOutlineProvider() {

        override fun getOutline(view: View?, outline: Outline?) {
            // 裁剪成圆形
            outline?.setOval(0,0,view?.width?:0,view?.height?:0)
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(view: View?) {
        if(v1.clipToOutline) {
            v1.clipToOutline = false  //关闭裁剪
        }
        else {
            v1.clipToOutline = true  // 打开裁剪
        }
    }
}
