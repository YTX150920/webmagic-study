package dao.impl;

import dao.StoryDao;
import model.Story;
import org.apache.ibatis.session.SqlSession;
import utils.MyBatisUtils;


public class StoryDaoImpl implements StoryDao {
    @Override
    public void insert(Story story) {

        //获取一个SqlSession对象
        SqlSession session = MyBatisUtils.getSqlSession();
        //添加操作，用insert方法，第一个参数必须是mapping中唯一的id的值。
        session.insert("insertStory", story);
        //涉及insert、update、delete的DML，要手动的commit呢，注意close方法是不会监测有木有commit，幻想close方法去commit会让你死的很惨滴。
        session.commit();
        //session也是相当于缓冲池技术一样的，所以用完也要记得close哦。
        session.close();

    }
}
