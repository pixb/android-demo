package com.pix.testshape

import android.app.Application

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 20-9-22
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
class Application:Application(){
    companion object {
        lateinit var INSTANCE:com.pix.testshape.Application
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}