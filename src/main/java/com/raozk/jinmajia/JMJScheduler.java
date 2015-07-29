package com.raozk.jinmajia;

import com.raozk.piprline.AnnouncementPipeline;
import com.raozk.zhongnan.*;
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
public class JMJScheduler {

    private static Logger logger = LoggerFactory.getLogger(JMJScheduler.class);

    @Resource(type = AnnouncementPipeline.class)
    AnnouncementPipeline AnnouncementPipeline;


    @Resource(type = JMJRuKuGongGaoPageCrawler.class)
    JMJRuKuGongGaoPageCrawler JMJRuKuGongGaoPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void JMJRuKuGongGaoPageCrawler() {
        Spider spider = Spider.create(JMJRuKuGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : JMJRuKuGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JMJShengGouGongGaoPageCrawler.class)
    JMJShengGouGongGaoPageCrawler JMJShengGouGongGaoPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void JMJShengGouGongGaoPageCrawler() {
        Spider spider = Spider.create(JMJShengGouGongGaoPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : JMJShengGouGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JMJPingTaiTongZhiPageCrawler.class)
    JMJPingTaiTongZhiPageCrawler JMJPingTaiTongZhiPageCrawler;

    @Scheduled(cron = "0 40 22 * * ?")
    public void JMJPingTaiTongZhiPageCrawler() {
        Spider spider = Spider.create(JMJPingTaiTongZhiPageCrawler).addPipeline(AnnouncementPipeline);
        for(String startUrl : JMJPingTaiTongZhiPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


}
