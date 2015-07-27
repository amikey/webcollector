package com.raozk.jiangsu;

import com.raozk.crawler.BaseCrawler;
import com.raozk.piprline.AnnouncementPipeline;
import com.raozk.scheduler.BaseSchduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class JSScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(JSScheduler.class);

    @Resource(type = JSTGGGPageCrawler.class)
    BaseCrawler jSTGGGPageCrawler;

    @Resource(type = JSSGGGPageCrawler.class)
    BaseCrawler jSSGGGPageCrawler;

    @Resource(type = AnnouncementPipeline.class)
    Pipeline pipeline;

    public void run() {

    }

    @Scheduled(cron = "0 15 14 * * ?")
    public void jSTGGGPageCrawler() {
        Spider spider = Spider.create(jSTGGGPageCrawler).addPipeline(pipeline);
        for(String startUrl : jSTGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 15 14 * * ?")
    public void jSSGGGPageCrawler() {
        Spider spider = Spider.create(jSSGGGPageCrawler).addPipeline(pipeline);
        for(String startUrl : jSSGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
