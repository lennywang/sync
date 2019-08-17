package com.xdclass;

import com.xdclass.business.DealBusiness;
import com.xdclass.business.QueryBusiness;
import com.xdclass.business.impl.DealBusinessImpl;
import com.xdclass.business.impl.QueryBusinessImpl;
import com.xdclass.thread.Consumer;
import com.xdclass.thread.Producer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 **/
public class Main {
    public static void main(String[] args) {
        QueryBusinessImpl queryBusiness = new QueryBusinessImpl();
        DealBusiness dealBusiness=new DealBusinessImpl();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(20));
        LinkedBlockingQueue<Runnable> runnables = new LinkedBlockingQueue<Runnable>(10);
        for (int i = 0; i < 10; i++) {
            try {
                runnables.put(new Consumer(dealBusiness,runnables));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        Producer producer = new Producer(queryBusiness, runnables, threadPoolExecutor);
        new Thread(producer).start();
    }
}
