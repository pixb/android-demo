package com.pix.testshape;

import java.io.Serializable;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author tangpengxiang on 2018/3/12.
 * @version 1.0.0
 * @description
 * @modify
 */
public class ShapeModule implements Serializable{
    public int layoutRes;
    public String title;

    public ShapeModule(int layoutRes, String title) {
        this.layoutRes = layoutRes;
        this.title = title;
    }
}
