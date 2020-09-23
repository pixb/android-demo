package com.pix.testshape

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pix.testshape.ext.btn_shape_selector
import com.pix.testshape.ext.dp
import com.pix.testshape.ext.pressDrawableSelector
import com.pix.testshape.ext.shape
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

/**
 * 展示shape的Demo
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        // 构建GradientDrawable对象并设置属性
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE // 矩形
            cornerRadius = 5.dp.toFloat() // 圆角
            colors = intArrayOf(Color.parseColor("#ff00ff"),Color.parseColor("#800000ff")) //渐变色
            gradientType = GradientDrawable.LINEAR_GRADIENT // 渐变类型
            orientation = GradientDrawable.Orientation.LEFT_RIGHT // 渐变方向
            setStroke(2.dp,Color.parseColor("#ffff00")) // 描边宽度和颜色
        }
        btn_codeshape.background = drawable
        btn_dslshape.background = shape {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 10f
            colors = intArrayOf(Color.parseColor("#ff00ff"),Color.parseColor("#800000ff"))
            gradientType = GradientDrawable.LINEAR_GRADIENT
            orientation = GradientDrawable.Orientation.LEFT_RIGHT
            setStroke(2.dp,Color.parseColor("#ffff00"))
        }

        var shapeNormal = shape {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 10f
            setColor(Color.BLUE)
            setStroke(2.dp,Color.parseColor("#ffff00"))
        }

        var shapePresssed = shape {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 10f
            setColor(Color.GRAY)
            setStroke(2.dp,Color.parseColor("#ffff00"))
        }

//        btn_selector.background = btn_shape_selector(shapeNormal,shapePresssed)

        btn_selector.background = pressDrawableSelector {
            pressedDrawable = shape {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10f
                setColor(Color.BLUE)
                setStroke(2.dp,Color.parseColor("#ffff00"))
            }
            unpressedDrawable = shape {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 10f
                setColor(Color.GRAY)
                setStroke(2.dp,Color.parseColor("#ffff00"))
            }
        }

    }
}