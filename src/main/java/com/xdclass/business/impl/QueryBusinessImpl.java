package com.xdclass.business.impl;

import com.xdclass.business.QueryBusiness;
import com.xdclass.middle.model.Student;
import com.xdclass.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;


/**
 *
 **/
public class QueryBusinessImpl implements QueryBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBusinessImpl.class);

    public List queryList(int count) {
        SqlSession middleSqlSession = SqlSessionUtil.getSqlSession(SqlSessionUtil.MIDDLE);
        List<Object> objectList = null;

        try {
            objectList = middleSqlSession.selectList("com.xdclass.middle.mapper.StudentMapper.selectList", count);
        } catch (Exception e) {
            LOGGER.error("查询发生异常===》", e);
            System.out.println(e);
            System.out.println("--分割线--");
            e.printStackTrace(System.out);
        } finally {
            middleSqlSession.close();
        }
        return objectList;
    }

    public int modifyListStatus(List data, String status) {
        List<Student> students =data;
        students.forEach(student ->{
            student.setDataStatus(status);
            SqlSession middleSqlSession= SqlSessionUtil.getSqlSession(SqlSessionUtil.MIDDLE);
            try {
                middleSqlSession.update("com.xdclass.middle.mapper.StudentMapper.updateByPrimaryKey",student);
                middleSqlSession.commit();
                System.out.println("update complete flag");
            }catch (Exception e){
                System.out.println("update fail");
                LOGGER.error("修改状态失败===》",e);
                e.printStackTrace(System.out);
            }finally {
                System.out.println("close");
                middleSqlSession.close();
            }
        });
        return 0;
    }
}
