package com.xdclass.thread;


import com.xdclass.business.QueryBusiness;
import com.xdclass.constant.DataStatusConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 **/
public class Producer implements Runnable {

    private static final Logger LOGGER= LoggerFactory.getLogger(Producer.class);

    private QueryBusiness queryBusiness;
    private LinkedBlockingQueue<Runnable> consumers;
    private ThreadPoolExecutor executor;

    public Producer(QueryBusiness queryBusiness, LinkedBlockingQueue<Runnable> consumers, ThreadPoolExecutor executor) {
        this.queryBusiness = queryBusiness;
        this.consumers = consumers;
        this.executor = executor;
    }

    public void run() {
        while (true){
            List list = queryBusiness.queryList(10);
            try {
                if (list!=null&&list.size()>0){
                    queryBusiness.modifyListStatus(list,DataStatusConstant.DEALING);
                    Consumer consumer = (Consumer) consumers.take();
                    consumer.setData(list);
                    executor.execute(consumer);
                }
            }catch (Exception e){
                LOGGER.error("生产者发生异常=====》",e);
                queryBusiness.modifyListStatus(list, DataStatusConstant.ERROR);
            }
        }
    }
}
