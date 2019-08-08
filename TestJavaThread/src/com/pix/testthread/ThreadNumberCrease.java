package com.pix.testthread;

/**
 * 这个类用来演示线程的互相调度
 * 演示多个线程执行输出0和1的互相变化
 */
public class ThreadNumberCrease {

    public static void main(String args[]) {
        NumberHolder holder = new NumberHolder();
        Thread iThread = new IncreaseThread(holder);
        Thread dThread = new DecreaseThread(holder);
        iThread.start();
        dThread.start();
    }

    private static class NumberHolder {
        private int number;
        public  synchronized void increase() {
            if(0 != number) {
                try {
                    wait();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            number++;
            System.out.println("NumberHolder#increase(),number:" + number);
            notify();
        }

        private synchronized void decrease() {
            if(0 == number) {
                try{
                    wait();
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            number--;
            System.out.println("NumberHolder#descrease(),number:" + number);
            notify();
        }
    }

    private static class IncreaseThread extends Thread {
        NumberHolder holder;
        public IncreaseThread(NumberHolder holder) {
            this.holder = holder;
        }
        @Override
        public void run() {
            for(int i = 0; i < 30; i++) {
                try {
                    Thread.sleep(1000 * (long)Math.random());
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                holder.increase();
            }
        }
    }

    private static class DecreaseThread extends Thread {
        NumberHolder holder;
        public DecreaseThread(NumberHolder holder) {
            this.holder = holder;
        }
        @Override
        public void run() {
            for(int i = 0 ; i < 30; i++) {
                try {
                    Thread.sleep(1000 * (long)Math.random());
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                holder.decrease();
            }
        }
    }

}
