package com.raozk.ybk001;

import com.raozk.crawler.BaseCrawler;
import com.raozk.piprline.MobilePipeline;
import com.raozk.piprline.RedisMobilePipeline;
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
public class Ybk001Scheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(Ybk001Scheduler.class);

    @Resource(type = Ybk001PageCrawler.class)
    BaseCrawler baseCrawler;

    @Resource(type = MobilePipeline.class)
    Pipeline pipeline;

    @Scheduled(cron = "0 0 0 * * ?")
    public void run() {
        Spider spider = Spider.create(baseCrawler).addPipeline(pipeline);
        for(String startUrl : baseCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
