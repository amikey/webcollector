package com.raozk.xx007;

import com.raozk.crawler.BaseCrawler;
import com.raozk.crawler.XX007PageCrawler;
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
public class XX007Scheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(XX007Scheduler.class);

    @Resource(type = XX007PageCrawler.class)
    BaseCrawler baseCrawler;

    @Resource(type = RedisMobilePipeline.class)
    Pipeline pipeline;

    @Scheduled(cron = "0 0 17 * * ?")
    public void run() {
        Spider spider = Spider.create(baseCrawler).addPipeline(pipeline).thread(50);
        for(String startUrl : baseCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
