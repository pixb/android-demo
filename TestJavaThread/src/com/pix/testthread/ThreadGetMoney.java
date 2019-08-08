package com.pix.testthread;

/**
 * 模仿从银行取钱
 * 银行类：有存款，有取钱方法
 * 取钱线程类：用于取钱
 */
public class ThreadGetMoney {
    public static void main(String args[]) {
        Bank bank = new Bank();
        Thread gt1 = new GetMoneyThread(bank);
        Thread gt2 = new GetMoneyThread(bank);
        gt1.start();
        gt2.start();
    }


    private static class Bank {
        private int money = 1000;

        private int getMoney(int num) {
            if(num < 0) {
               return -1;  // 取钱时不能小于0
            }
            if(num > money) {
                return -2; //取钱数不能多余银行存款
            }
            if(money < 0) {
                return -3; // 银行没钱不能取
            }
            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            money -= num;
            System.out.println("Banl left money:" + money);
            return num;
        }
    }

    private static class GetMoneyThread extends Thread {
        private Bank bank;
        public GetMoneyThread(Bank bank) {
            this.bank = bank;
        }
        @Override
        public void run() {
            System.out.println("GetMoneyThread number:" + bank.getMoney(800));
        }
    }
}
