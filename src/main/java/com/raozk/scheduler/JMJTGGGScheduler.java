package com.raozk.scheduler;

import com.raozk.crawler.BaseCrawler;
import com.raozk.crawler.JMJTGGGPageCrawler;
import com.raozk.crawler.ZNTGGGPageCrawler;
import com.raozk.piprline.RedisGGPipeline;
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
public class JMJTGGGScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(JMJTGGGScheduler.class);

    @Resource(type = JMJTGGGPageCrawler.class)
    BaseCrawler baseCrawler;

    @Resource(type = RedisGGPipeline.class)
    Pipeline pipeline;

    @Scheduled(cron = "0 * * * * ?")
    public void run() {
        Spider spider = Spider.create(baseCrawler).addPipeline(pipeline);
        for(String startUrl : baseCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
