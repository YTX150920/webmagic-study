package processor;

import pipeline.MybatisPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class StoryProcessor implements PageProcessor{

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("title", page.getHtml().xpath("//h2[@class='chapter']/text()").toString());
        if (page.getResultItems().get("title") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("content", page.getHtml().xpath("//div[@class='article-content entry']/allText()"));
        page.putField("post_url", page.getUrl().regex("http://zangmizhe.com/chapter/index/cid-\\d+.html").toString());

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(http://zangmizhe.com/chapter/index/cid-\\d+.html)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        Spider.create(new StoryProcessor())
                //从开始页面抓
                .addUrl("http://zangmizhe.com/")
                        //输出到控制台
                .addPipeline(new MybatisPipeline())
                        //开启5个线程抓取
                .thread(1)
                        //启动爬虫
                .run();
    }

}
