package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {

    static SqlSessionFactory  sqlSessionFactory = null;
    static {
        try {
            //配置连接数据库的资源文件
            String resource="mybatis-conf.xml";
            //通过io流读取资源文件信息
            InputStream  inputStream =Resources.getResourceAsStream(resource);
            //通过SqlSessionFactoryBuilder创建SqlSessionFactory对象，同时传递io对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    /**
     * 获得sqlSession对象该对象中有实例化接口的方法，同时SqlSession底层封装是jdbc信息，connection
     * @return
     */
    public static SqlSession getSqlSession(){

        return sqlSessionFactory.openSession();
    }

}