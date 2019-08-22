package com.pix.testgreendao.service;

/**
 * Created by Administrator on 2017/3/28.
 */

public class StudentFactory {
    public static IStudentService getStudentService() {
        return new StudentServiceImpl();
    }
}
