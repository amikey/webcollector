package com.raozk.nanfang;

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
public class NFScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(NFScheduler.class);

    @Resource(type = NFTZGGPageCrawler.class)
    BaseCrawler nFTZGGPageCrawler;

    @Resource(type = NFSGGGPageCrawler.class)
    BaseCrawler nFSGGGPageCrawler;

    @Resource(type = NFTGGGPageCrawler.class)
    BaseCrawler nFTGGGPageCrawler;

    @Resource(type = NFZXPageCrawler.class)
    BaseCrawler nFZXPageCrawler;

    @Resource(type = AnnouncementPipeline.class)
    Pipeline announcementPipeline;

    @Resource(type = AdvisoryNewsPipeline.class)
    Pipeline advisoryNewsPipeline;

    public void run() {
    }

    @Scheduled(cron = "0 45 * * * ?")
    public void nFTZGGPage() {
        Spider spider = Spider.create(nFTZGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : nFTZGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 45 * * * ?")
    public void nFSGGGPage() {
        Spider spider = Spider.create(nFSGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : nFSGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 45 * * * ?")
    public void nFTGGGPage() {
        Spider spider = Spider.create(nFTGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : nFTGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 45 * * * ?")
    public void zxPage() {
        Spider spider = Spider.create(nFZXPageCrawler).addPipeline(advisoryNewsPipeline);
        for(String startUrl : nFZXPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
