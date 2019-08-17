package com.xdclass.business;


import java.util.List;

public interface QueryBusiness {

    /**
     * 查询数据
     **/
    List queryList(int count);

    /**
     * 修改数据状态
     **/
    int modifyListStatus(List data,String status);
}
