package com.lonie.biz.consumer;

/**
 * @author huzeming Created time 2020/3/26 : 10:50 上午 Desc:
 */

public class Producer {

    private Repository mRepository;

    public Producer(Repository repository) {
        mRepository = repository;
    }

    public boolean produce(String name) {

        synchronized (mRepository) {
            try {
                boolean notfull = mRepository.in(name);
                if (!notfull) {
                    System.out.println("工厂放假了");
                    mRepository.wait();
                    return false;
                }
                mRepository.notify();
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
