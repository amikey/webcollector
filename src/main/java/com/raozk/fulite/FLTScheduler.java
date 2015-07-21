package com.raozk.fulite;

import com.raozk.crawler.BaseCrawler;
import com.raozk.piprline.AdvisoryNewsPipeline;
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
public class FLTScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(FLTScheduler.class);

    @Resource(type = FLTSGGGPageCrawler.class)
    BaseCrawler fLTSGGGPageCrawler;

    @Resource(type = FLTTGGGPageCrawler.class)
    BaseCrawler fLTTGGGPageCrawler;

    @Resource(type = FLTZXPageCrawler.class)
    BaseCrawler fLTZXPageCrawler;

    @Resource(type = AnnouncementPipeline.class)
    Pipeline announcementPipeline;

    @Resource(type = AdvisoryNewsPipeline.class)
    Pipeline advisoryNewsPipeline;

    public void run() {
    }

    @Scheduled(cron = "0 22 * * * ?")
    public void fLTSGGGPage() {
        Spider spider = Spider.create(fLTSGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : fLTSGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 22 * * * ?")
    public void fLTTGGGPage() {
        Spider spider = Spider.create(fLTTGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : fLTTGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 22 * * * ?")
    public void zxPage() {
        Spider spider = Spider.create(fLTZXPageCrawler).addPipeline(advisoryNewsPipeline);
        for(String startUrl : fLTZXPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
