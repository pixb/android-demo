package com.pix.testjavathread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 测试锁重入
 */
public class SynchronizedDemo {
        synchronized void f1() {
        System.out.println("Synchronized f1 start!");
        // 休眠2s
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        f2();
        System.out.println("Synchronized f1 end!");
        List list = new ArrayList();
    }

    synchronized void f2() {
        System.out.println("Synchronized f2 start!");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Synchronized f2 end!");
    }

    public static void main(String args []) {
        new SynchronizedDemo().f1();
    }
}
