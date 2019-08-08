package com.pix.testpropertyanimation.utils;

import android.content.res.Resources;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author tangpengxiang on 2018/3/1.
 * @version 1.0.0
 * @description
 * @modify
 */
public class Utils {
    public static int dp2pix(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
