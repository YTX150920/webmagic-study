package pipeline;

import dao.StoryDao;
import dao.impl.StoryDaoImpl;
import model.Story;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;


public class MybatisPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {

        StoryDao storyDao = new StoryDaoImpl();

        Story story = new Story();

        //遍历所有结果，存进数据库
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getKey().equals("title")) {
                story.setTitle(entry.getValue().toString());
            } else if (entry.getKey().equals("post_url")) {
                story.setPost_url(entry.getValue().toString());
            } else if (entry.getKey().equals("content")) {
                story.setContent(entry.getValue().toString());
            }
        }
        storyDao.insert(story);
    }
}
