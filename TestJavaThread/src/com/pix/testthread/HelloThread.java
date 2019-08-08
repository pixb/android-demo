package com.pix.testthread;

/**
 * 这个类用来演示线程实现的两种方法
 *  方法一：继承Thread类
 *  方法二：实现Runnable接口
 */
public class HelloThread {
    public static void main(String args[]) {
       ThreadOne threadOne = new ThreadOne();
       threadOne.start();
       Thread threadTwo = new Thread(new ThreadTwo());
       threadTwo.start();
    }

    private static class ThreadOne extends Thread {
        @Override
        public void run() {
            super.run();
            for (int  i = 0; i < 100; i++) {
                System.out.println("ThreadOne print:------> " + i);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ThreadTwo implements Runnable {
        @Override
        public void run() {
            for (int  i = 0; i < 100; i++) {
                System.out.println("ThreadTwo print:-----------------> " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
