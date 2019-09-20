package com.louis.mybatis.dynamic.app;

import com.louis.mybatis.dynamic.entity.LocalUser;
import com.louis.mybatis.dynamic.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * @author John·Louis
 * @date create in 2019/8/31
 * description:
 */
public class App {

    private static SqlSessionFactory sqlSessionFactory;

    private static Reader reader;



    static {
        try {
            reader = Resources.getResourceAsReader("mybatis/SqlMapConfig.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static SqlSession getSession() {
        return sqlSessionFactory.openSession(true);
    }


    private  static <T>  T getMapper(SqlSession sqlSession,Class<T> tClass) {
        return sqlSession.getMapper(tClass);
    }

    public static void main(String[] args) {
//        insert();
//        getUser();
//        getAllUser();
//        inser2();



    }
    private static void insert() {
        LocalUser user = new LocalUser();
        SqlSession session = getSession();
//        user.setId(2);
        user.setUsername("lisi");
        user.setAge(30);
        user.setFivarite("fivarite");

        UserMapper mapper = getMapper(session,UserMapper.class);
        int i = mapper.inserUser(user);
//        session.commit();
        System.out.println(i);
    }

    public static void getUser() {
        SqlSession session = getSession();
        UserMapper mapper = getMapper(session,UserMapper.class);
        LocalUser user = mapper.getUser(2);
        System.out.println(user);
    }

    public static void getAllUser() {
        SqlSession session = getSession();
        UserMapper mapper = getMapper(session,UserMapper.class);
        List<LocalUser> user = mapper.selectAll();
        System.out.println(user);
    }

    public static void inser2() {
        LocalUser user = new LocalUser();
        SqlSession session = getSession();
//        user.setId(2);
        user.setUsername("lisi");
        user.setAge(30);
        user.setFivarite("fivarite");

        UserMapper mapper = getMapper(session,UserMapper.class);
        int i = mapper.inser2(user);
//        session.commit();
        System.out.println(i);
    }



}
