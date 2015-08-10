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

    @Scheduled(cron = "0 0/1 * * * ?")
    public void nFTZGGPage() {
        Spider spider = Spider.create(nFTZGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : nFTZGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void nFSGGGPage() {
        Spider spider = Spider.create(nFSGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : nFSGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void nFTGGGPage() {
        Spider spider = Spider.create(nFTGGGPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : nFTGGGPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void zxPage() {
        Spider spider = Spider.create(nFZXPageCrawler).addPipeline(advisoryNewsPipeline);
        for(String startUrl : nFZXPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = NFJiaoYiShuJUPageCrawler.class)
    BaseCrawler NFJiaoYiShuJUPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void NFJiaoYiShuJUPageCrawler() {
        Spider spider = Spider.create(NFJiaoYiShuJUPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : NFJiaoYiShuJUPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = NFTingPaiGongGaoPageCrawler.class)
    BaseCrawler NFTingPaiGongGaoPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void NFTingPaiGongGaoPageCrawler() {
        Spider spider = Spider.create(NFTingPaiGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : NFTingPaiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


    @Resource(type = NFTeShuGongGaoPageCrawler.class)
    BaseCrawler NFTeShuGongGaoPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void NFTeShuGongGaoPageCrawler() {
        Spider spider = Spider.create(NFTeShuGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : NFTeShuGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = NFKuCunGongGaoPageCrawler.class)
    BaseCrawler NFKuCunGongGaoPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void NFKuCunGongGaoPageCrawler() {
        Spider spider = Spider.create(NFKuCunGongGaoPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : NFKuCunGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = NFShouGouJiaoYiPageCrawler.class)
    BaseCrawler NFShouGouJiaoYiPageCrawler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void NFShouGouJiaoYiPageCrawler() {
        Spider spider = Spider.create(NFShouGouJiaoYiPageCrawler).addPipeline(announcementPipeline);
        for(String startUrl : NFShouGouJiaoYiPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }



}
