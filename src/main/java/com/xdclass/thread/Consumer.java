package com.xdclass.thread;

import com.xdclass.business.DealBusiness;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 **/
public class Consumer implements Runnable {

    private List data;
    private DealBusiness dealBusiness;
    private LinkedBlockingQueue<Runnable> consumers;

    public Consumer(DealBusiness dealBusiness, LinkedBlockingQueue<Runnable> consumers) {
        this.dealBusiness = dealBusiness;
        this.consumers = consumers;
    }

    public void run() {
        try {
            dealBusiness.deal(data);
        }finally {
            try {
                consumers.put(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(List data) {
        this.data = data;
    }
}
