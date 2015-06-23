package com.raozk.scheduler;

import com.raozk.crawler.BaseCrawler;
import com.raozk.crawler.ZNSGGGPageCrawler;
import com.raozk.crawler.ZNTZGGPageCrawler;
import com.raozk.piprline.RedisGGPipeline;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class ZNTZGGScheduler implements BaseSchduler {

    @Resource(type = ZNTZGGPageCrawler.class)
    BaseCrawler baseCrawler;

    @Resource(type = RedisGGPipeline.class)
    Pipeline pipeline;

    public void run() {
        Spider spider = Spider.create(baseCrawler).addPipeline(pipeline);
        for(String startUrl : baseCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.runAsync();
    }

    @PostConstruct
    public void start(){
        run();
    }
}
