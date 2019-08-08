package com.pix.testthread;

/**
 * 测试线程访问成员变量问题
 * 多个线程访问同一个类，同一对象的成员变量
 */
public class ThreadVariateOne {
    public static void main(String args []) {
        ThreadOne t = new ThreadOne();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();
    }

     static class ThreadOne implements Runnable {
        int i  ;
        @Override
        public void run() {
            while(true) {
                System.out.println("ThreadOne num:" + i++);
                try {
                    Thread.sleep(1000 * (long)Math.random());
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
               if(50 == i) {
                   break;
               }
            }
        }
    }
}
