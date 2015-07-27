package com.raozk.nanjing;

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
public class NJScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(NJScheduler.class);

    @Resource(type = TongZhiGongGaoPageCrawler.class)
    BaseCrawler tongZhiGongGaoPageCrawler;

    @Resource(type = AnnouncementPipeline.class)
    Pipeline pipeline;

    @Scheduled(cron = "0/5 * * * * ?")
    public void tongZhiGongGaoPageCrawler() {
        Spider spider = Spider.create(tongZhiGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : tongZhiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JiaoYiShuJuPageCrawler.class)
    BaseCrawler JiaoYiShuJuPageCrawler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void JiaoYiShuJuPageCrawler() {
        Spider spider = Spider.create(JiaoYiShuJuPageCrawler).addPipeline(pipeline);
        for(String startUrl : JiaoYiShuJuPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = ShenGouGongGaoPageCrawler.class)
    BaseCrawler ShenGouGongGaoPageCrawler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void ShenGouGongGaoPageCrawler() {
        Spider spider = Spider.create(ShenGouGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : ShenGouGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = TuoGuanGongGaoPageCrawler.class)
    BaseCrawler TuoGuanGongGaoPageCrawler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void TuoGuanGongGaoPageCrawler() {
        Spider spider = Spider.create(TuoGuanGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : TuoGuanGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = XianHuoCanKaoJiaPageCrawler.class)
    BaseCrawler XianHuoCanKaoJiaPageCrawler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void XianHuoCanKaoJiaPageCrawler() {
        Spider spider = Spider.create(XianHuoCanKaoJiaPageCrawler).addPipeline(pipeline);
        for(String startUrl : XianHuoCanKaoJiaPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = RuKuGongGaoPageCrawler.class)
    BaseCrawler RuKuGongGaoPageCrawler;

    @Scheduled(cron = "0/5 * * * * ?")
    public void RuKuGongGaoPageCrawler() {
        Spider spider = Spider.create(RuKuGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : RuKuGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


    public void run() {

    }

}
