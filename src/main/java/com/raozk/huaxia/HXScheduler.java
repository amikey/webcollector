package com.raozk.huaxia;

import com.raozk.crawler.BaseCrawler;
import com.raozk.nanfang.*;
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
public class HXScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(HXScheduler.class);

    @Resource(type = AnnouncementPipeline.class)
    Pipeline announcementPipeline;

    public void run() {
    }

    @Resource(type = HXGongGaoPageCrawler.class)
    HXGongGaoPageCrawler HXGongGaoPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void HXGongGaoPageCrawler() {
        Spider spider = Spider.create(HXGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : HXGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = HXTuoGuanGongGaoPageCrawler.class)
    HXTuoGuanGongGaoPageCrawler HXTuoGuanGongGaoPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void HXTuoGuanGongGaoPageCrawler() {
        Spider spider = Spider.create(HXTuoGuanGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : HXTuoGuanGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = HXShenGouGongGaoPageCrawler.class)
    HXShenGouGongGaoPageCrawler HXShenGouGongGaoPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void HXShenGouGongGaoPageCrawler() {
        Spider spider = Spider.create(HXShenGouGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : HXShenGouGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
