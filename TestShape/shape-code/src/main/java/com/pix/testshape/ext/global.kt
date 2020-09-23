package com.pix.testshape.ext

import android.content.res.Resources
import android.util.TypedValue
import com.pix.testshape.Application

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 20-9-22
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
inline var Int.dp:Int
    get() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this.toFloat(),
                Resources.getSystem().displayMetrics
        ).toInt()
    }
    set(value) {}
