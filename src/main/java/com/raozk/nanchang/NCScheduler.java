package com.raozk.nanchang;

import com.raozk.crawler.BaseCrawler;
import com.raozk.piprline.AdvisoryNewsPipeline;
import com.raozk.piprline.AnnouncementPipeline;
import com.raozk.scheduler.BaseSchduler;
import com.raozk.shanghai.SHTZGGPageCrawler;
import com.raozk.shanghai.SHZXPageCrawler;
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
public class NCScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(NCScheduler.class);

    @Resource(type = AnnouncementPipeline.class)
    Pipeline announcementPipeline;

    public void run() {
    }

    @Resource(type = NCGGPageCrawler.class)
    NCGGPageCrawler NCGGPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void NCGGPageCrawler() {
        Spider spider = Spider.create(NCGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : NCGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
