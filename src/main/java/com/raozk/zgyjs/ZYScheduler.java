package com.raozk.zgyjs;

import com.raozk.crawler.BaseCrawler;
import com.raozk.piprline.AnnouncementPipeline;
import com.raozk.scheduler.BaseSchduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class ZYScheduler {

    private static Logger logger = LoggerFactory.getLogger(ZYScheduler.class);

    @Resource(type = AnnouncementPipeline.class)
    AnnouncementPipeline AnnouncementPipeline;

    @Resource(type = ZYZhongXinGongGaoPageCrawler.class)
    ZYZhongXinGongGaoPageCrawler ZYZhongXinGongGaoPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZYZhongXinGongGaoPageCrawler() {
        Spider spider = Spider.create(ZYZhongXinGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZYZhongXinGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = ZYTuoGuanGongGaoPageCrawler.class)
    ZYTuoGuanGongGaoPageCrawler ZYTuoGuanGongGaoPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZYTuoGuanGongGaoPageCrawler() {
        Spider spider = Spider.create(ZYTuoGuanGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZYTuoGuanGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


    @Resource(type = ZYShengGouGongGaoPageCrawler.class)
    ZYShengGouGongGaoPageCrawler ZYShengGouGongGaoPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZYShengGouGongGaoPageCrawler() {
        Spider spider = Spider.create(ZYShengGouGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZYShengGouGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


}
