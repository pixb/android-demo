package com.pix.testthread;

import java.lang.reflect.Method;

/**
 * 测试多线程遇到多个方法的同步问题
 * 一个类包含两个方法
 * 两个线程分别执行这两个方法
 * 两个方法的同步问题
 */
public class ThreadMoreMethod {

    public static void main(String [] args) {
        MoreMethod mm = new MoreMethod();
        Thread t1 = new ThreadMethodOne(mm);
        Thread t2 = new ThreadMethodTwo(mm);
        t1.start();
        t2.start();
    }

    static class MoreMethod {
        public synchronized void func1() {
            for(int i  = 0; i < 100; i++) {
                System.out.println("MoreMethod#func1(),i------>" + i);
                try {
                    Thread.sleep(100);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }
        public synchronized void func2() {
            for(int i  = 0; i < 100; i++) {
                System.out.println("MoreMothod#func2(),i-------------->" + i);
                try {
                    Thread.sleep(100);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ThreadMethodOne extends Thread{
        private MoreMethod mm;
        public ThreadMethodOne(MoreMethod mm) {
            this.mm = mm;
        }
       @Override
       public void run() {
           mm.func1();
       }
    }
    static class ThreadMethodTwo extends Thread {
        private MoreMethod mm;
        public ThreadMethodTwo(MoreMethod mm) {
            this.mm = mm;
        }
        @Override
        public void run() {
            mm.func2();
        }
    }
}
