package com.raozk.huazhong;

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
public class HZScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(HZScheduler.class);

    @Resource(type = AnnouncementPipeline.class)
    Pipeline announcementPipeline;

    @Resource(type = AdvisoryNewsPipeline.class)
    Pipeline advisoryNewsPipeline;

    public void run() {
    }

    @Resource(type = HZGongGaoPageCrawler.class)
    BaseCrawler HZGongGaoPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void hzGGGGPage() {
        Spider spider = Spider.create(HZGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : HZGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


}
