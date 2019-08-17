package com.xdclass.business.impl;

import com.xdclass.business.DealBusiness;
import com.xdclass.constant.DataStatusConstant;
import com.xdclass.middle.model.Student;
import com.xdclass.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 **/
public class DealBusinessImpl implements DealBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealBusinessImpl.class);

    public void deal(List data) {
        List<com.xdclass.our.model.Student> students = adapter(data);
        students.forEach(student -> {
            student.setName(student.getName() + "test");
            SqlSession ourSqlSession = SqlSessionUtil.getSqlSession(SqlSessionUtil.OUR);
            try {
                ourSqlSession.insert("com.xdclass.our.mapper.StudentMapper.insertSelective", student);
                ourSqlSession.commit();
                modifyMiddle(student.getId(), DataStatusConstant.FINISH);
            } catch (Exception e) {
                LOGGER.error("数据处理发生异常===》", e);
                modifyMiddle(student.getId(), DataStatusConstant.ERROR);
            } finally {
                ourSqlSession.close();
            }
        });
    }

    private void modifyMiddle(int id,String status)
    {
        Student student=new Student();
        student.setId(id);
        student.setDataStatus(status);
        student.setDealTime(new Date());
        SqlSession middleSqlSession = SqlSessionUtil.getSqlSession(SqlSessionUtil.MIDDLE);
        try {
            middleSqlSession.update("com.xdclass.middle.mapper.StudentMapper.updateStatusById", student);
            middleSqlSession.commit();

        }catch (Exception e){
            LOGGER.error("修改中间表失败===》",e);
        }finally {
            middleSqlSession.close();
        }
    }

    private List<com.xdclass.our.model.Student> adapter(List<Student> students){
        ArrayList<com.xdclass.our.model.Student> result = new ArrayList<>();
        students.forEach(student->{
            com.xdclass.our.model.Student stu = new com.xdclass.our.model.Student();
            stu.setDepartment(student.getDepartment());
            stu.setSex(student.getSex());
            stu.setId(student.getId());
            stu.setName(student.getName());
            stu.setBirth(student.getBirth());
            stu.setAddTime(new Date());
            result.add(stu);
        });
        return  result;
    }
}
