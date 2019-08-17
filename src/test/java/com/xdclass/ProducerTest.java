package com.xdclass;

import com.xdclass.business.QueryBusiness;
import com.xdclass.business.impl.QueryBusinessImpl;
import org.junit.Test;

import java.util.List;

public class ProducerTest {

    @Test
    public void testList(){
        QueryBusiness queryBusiness = new QueryBusinessImpl();
        List list = queryBusiness.queryList(2);
        System.out.println(list);
    }

    @Test
    public void testUpdate(){
        QueryBusiness queryBusiness = new QueryBusinessImpl();
        //QueryBusiness queryBusiness = new QryBusiImpl();
        List list = queryBusiness.queryList(2);
        queryBusiness.modifyListStatus(list, "10D");
    }
}
