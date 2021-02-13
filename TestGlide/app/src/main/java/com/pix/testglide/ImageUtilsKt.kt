package com.pix.testglide

import android.os.FileUtils
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

/**
 * Copyright (C), 2020-2020, guagua
 * Author: pix
 * Date: 20-11-25
 * Version: 1.0
 * Description:
 * History:
 * <author> <time> <version> <desc>
 */
class ImageUtilsKt {
    companion object {
        fun fileToBase64String(file: File): String {
            var data = file2byte(file)
            return Base64.encodeToString(data, Base64.DEFAULT)
        }

        private fun file2byte(file: File): ByteArray? {
            return if (!file.exists()) {
                null
            } else {
                var buffer: ByteArray? = null
                try {
                    val fis = FileInputStream(file)
                    val bos = ByteArrayOutputStream()
                    val b = ByteArray(1024)
                    var n: Int
                    while (fis.read(b).also { n = it } != -1) {
                        bos.write(b, 0, n)
                    }
                    fis.close()
                    bos.close()
                    buffer = bos.toByteArray()
                } catch (var6: Exception) {
                }
                buffer
            }
        }

    }
}