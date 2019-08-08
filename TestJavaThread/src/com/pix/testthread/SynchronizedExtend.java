package com.pix.testthread;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * 测试继承锁重入
 */
public class SynchronizedExtend {
    public static void main(String [] args) {
        new SynchronizedSub().m1();
    }

    private static class SynchroizedSup {
        public synchronized void  m1() {
            System.out.println("Sup m1 start!");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Sup m1 end!");
        }
    }

    private static class SynchronizedSub extends SynchroizedSup {
        @Override
        public synchronized void m1() {
            System.out.println("Sub m1 start!");
            super.m1();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Sub m1 end!");
        }
    }
}
