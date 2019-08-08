package com.pix.testthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池测试
 *  1. 创建缓冲线程池，执行代码
 */
public class TestThreadPool {
    public static void main(String args[]) {
//        testCacheThreadPool();
//        testFixedThreadPool();
        testScheduledThreadPool();
        testScheduledRateThreadPool();
    }


    /**
     * 测试缓冲线程池使用
     */
    private static void testCacheThreadPool() {
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for(int i  = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            int finalI = i;
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("testCacheThreadPool," + Thread.currentThread().getName() + ",i:" + finalI);
                }
            });
        }
    }

    /**
     * 测试线程定长线程池
     */
    private static void testFixedThreadPool() {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("testFixedThreadPool()," + Thread.currentThread().getName() + ",index:" + index);
                }
            });
        }
    }

    /**
     * 测试定时线程池
     */
    private static void testScheduledThreadPool() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
        threadPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("testSceduledThreadPool()===========");
            }
        },3, TimeUnit.SECONDS);
    }


    private static void testScheduledRateThreadPool() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
        threadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        },1,3,TimeUnit.SECONDS);
    }


}
