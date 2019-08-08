package com.pix.testthread;

/**
 * 测试volatile关键字使用
 * 两个线程一个修改，一个读取
 * 使用volatile避免死锁
 */
public class TestThreadVolatile {
    public static void main(String args[]) {
        ModifyString ms = new ModifyString();
        // 改变字符串线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(true) {
                    ms.put("String:" + System.currentTimeMillis());
                }
            }
        }.start();

        // 读取字符串线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                while(true) {
                    ms.getString();
                }
            }
        }.start();
    }

    private static class ModifyString {
        private String value;
        private volatile boolean isNewString;

        public void put(String s) {
            if(null == s) {
                return ;
            }
            while (isNewString) { // 有新的，未被取走就等待

            }
            value = s;
            isNewString = true;
            System.out.println("ModifyString,put(),s:" + s);
        }
        public String getString() {
           while(!isNewString) { // 没新的，就等待

           }
           isNewString = false;
           System.out.println("ModifyString,get(),value:" + value);
           return value;
        }
    }
}
