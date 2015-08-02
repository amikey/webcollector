package com.raozk.zhongnan;

import com.raozk.crawler.BaseCrawler;
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
public class ZNScheduler{

    private static Logger logger = LoggerFactory.getLogger(ZNScheduler.class);

    @Resource(type = AnnouncementPipeline.class)
    AnnouncementPipeline AnnouncementPipeline;

    @Resource(type = ZNTongZhiGongGaoPageCrawler.class)
    ZNTongZhiGongGaoPageCrawler ZNTongZhiGongGaoPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZNTongZhiGongGaoPageCrawler() {
        Spider spider = Spider.create(ZNTongZhiGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZNTongZhiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = ZNJiaoYiShuJuPageCrawler.class)
    ZNJiaoYiShuJuPageCrawler ZNJiaoYiShuJuPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZNJiaoYiShuJuPageCrawler() {
        Spider spider = Spider.create(ZNJiaoYiShuJuPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZNJiaoYiShuJuPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = ZNShengGouGongGaoPageCrawler.class)
    ZNShengGouGongGaoPageCrawler ZNShengGouGongGaoPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZNShengGouGongGaoPageCrawler() {
        Spider spider = Spider.create(ZNShengGouGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZNShengGouGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = ZNTuoGuanGongGaoPageCrawler.class)
    ZNTuoGuanGongGaoPageCrawler ZNTuoGuanGongGaoPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZNTuoGuanGongGaoPageCrawler() {
        Spider spider = Spider.create(ZNTuoGuanGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZNTuoGuanGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


    @Resource(type = ZNXianHuoCanKaoJiaPageCrawler.class)
    ZNXianHuoCanKaoJiaPageCrawler ZNXianHuoCanKaoJiaPageCrawler;

    @Scheduled(cron = "0 0 * * * ?")
    public void ZNXianHuoCanKaoJiaPageCrawler() {
        Spider spider = Spider.create(ZNXianHuoCanKaoJiaPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : ZNXianHuoCanKaoJiaPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

}
