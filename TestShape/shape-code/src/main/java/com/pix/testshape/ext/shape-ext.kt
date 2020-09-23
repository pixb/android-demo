package com.pix.testshape.ext

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 20-9-22
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
inline var View.shape: GradientDrawable
    get() {
        return GradientDrawable()
    }
    set(value) {
        background = value
    }

inline fun shape(init: GradientDrawable.() -> Unit) = GradientDrawable().apply(init)

inline fun drawable_selector(list:List<Pair<IntArray, Drawable?>>):StateListDrawable {
    return StateListDrawable().apply {
        list.forEach {
            addState(it.first, it.second)
        }
    }
}

inline fun pressDrawableSelector(unpressedDrawable:Drawable, pressedDrawable:Drawable):StateListDrawable {
    var normalPair = Pair(STATE_UNPRESSED,unpressedDrawable)
    var pressedPair = Pair(STATE_PRESSED,pressedDrawable)
    return drawable_selector(listOf(normalPair,pressedPair))
}

inline fun pressDrawableSelector(drawable:PressDrawable.() -> Unit):StateListDrawable {
    var inDrawable = PressDrawable().apply(drawable)
    var normalPair = Pair(STATE_UNPRESSED,inDrawable.unpressedDrawable)
    var pressedPair = Pair(STATE_PRESSED,inDrawable.pressedDrawable)
    return drawable_selector(listOf(normalPair,pressedPair))
}

class PressDrawable() {
    var unpressedDrawable:Drawable? = null
    var pressedDrawable:Drawable? = null
}

// 给常量取一个较短的别名以增加可读性
val STATE_ENABLE = intArrayOf(android.R.attr.state_enabled)
val STATE_DISABLE = intArrayOf(-android.R.attr.state_enabled)
val STATE_PRESSED = intArrayOf(android.R.attr.state_pressed)
val STATE_UNPRESSED = intArrayOf(-android.R.attr.state_pressed)


