package com.xdclass.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 *
 **/
public class SqlSessionUtil {
    private static SqlSessionFactory ourSqlSessionFactory;
    private static SqlSessionFactory middleSqlSessionFactory;
    public static final String OUR = "app";
    public static final String MIDDLE = "middle";

    private static final String CONFIG_MIDDLE = "mybatis-config-middle.xml";
    private static final String CONFIG_OUR = "mybatis-config-our.xml";


    private static Reader middleResourceAsReader = null;
    private static Reader ourResourceAsReader = null;

    static {
        try {
            middleResourceAsReader = Resources.getResourceAsReader(CONFIG_MIDDLE);
            ourResourceAsReader = Resources.getResourceAsReader(CONFIG_OUR);

            middleSqlSessionFactory = new SqlSessionFactoryBuilder().build(middleResourceAsReader);
            ourSqlSessionFactory = new SqlSessionFactoryBuilder().build(ourResourceAsReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                middleResourceAsReader.close();
                ourResourceAsReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SqlSession getSqlSession(String code) {
        if (code.equals(MIDDLE))
            return middleSqlSessionFactory.openSession();
        return ourSqlSessionFactory.openSession();
    }
}
