package com.lonie.biz.consumer;

/**
 * @author huzeming Created time 2020/3/26 : 11:34 上午 Desc:
 */

public class Test01 {

    Consumer c;
    Producer p;

    public static void main(String[] args) {
        Consumer c, c2, c3;
        Producer p1, p2;
        Repository repository = new Repository();

        c = new Consumer(repository);
        c2 = new Consumer(repository);
        c3 = new Consumer(repository);
        Producer p = new Producer(repository);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                   boolean in =  p.produce("商品：" + i);
                   if (!in){
                       i--;
                   }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    c3.consume();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    c2.consume();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    c.consume();
                }
            }
        }).start();

    }
}
