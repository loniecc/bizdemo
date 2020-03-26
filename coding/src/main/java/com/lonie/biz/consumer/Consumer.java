package com.lonie.biz.consumer;

/**
 * @author huzeming Created time 2020/3/26 : 10:50 上午 Desc:
 */

public class Consumer {

    private Repository mRepository;

    public Consumer(Repository repository) {
        mRepository = repository;
    }

    public void consume() {

        synchronized (mRepository) {

            String good = mRepository.out();
            try {
                if (good == null) {
                    mRepository.wait();
                }
                System.out.println(Thread.currentThread() + " consume good " + good);
                mRepository.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
