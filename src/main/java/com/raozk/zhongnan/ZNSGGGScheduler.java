package com.raozk.zhongnan;

import com.raozk.crawler.BaseCrawler;
import com.raozk.scheduler.BaseSchduler;
import com.raozk.piprline.AnnouncementPipeline;
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
public class ZNSGGGScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(ZNSGGGScheduler.class);

    @Resource(type = ZNSGGGPageCrawler.class)
    BaseCrawler baseCrawler;

    @Resource(type = AnnouncementPipeline.class)
    Pipeline pipeline;

    @Scheduled(cron = "0 15 14 * * ?")
    public void run() {
        Spider spider = Spider.create(baseCrawler).addPipeline(pipeline);
        for(String startUrl : baseCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
