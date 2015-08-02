package com.raozk.jiangsu;

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
public class JSScheduler implements BaseSchduler {

    private static Logger logger = LoggerFactory.getLogger(JSScheduler.class);


    @Resource(type = AnnouncementPipeline.class)
    Pipeline pipeline;

    public void run() {

    }

    @Resource(type = JSGuaPaiGongGaoPageCrawler.class)
    JSGuaPaiGongGaoPageCrawler JSGuaPaiGongGaoPageCrawler;


    @Scheduled(cron = "0 0 * * * ?")
    public void JSGuaPaiGongGaoPageCrawler() {
        Spider spider = Spider.create(JSGuaPaiGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : JSGuaPaiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JSZhongQianGongGaoPageCrawler.class)
    JSZhongQianGongGaoPageCrawler JSZhongQianGongGaoPageCrawler;


    @Scheduled(cron = "0 0 * * * ?")
    public void JSZhongQianGongGaoPageCrawler() {
        Spider spider = Spider.create(JSZhongQianGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : JSZhongQianGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JSZaiGuaPaiGongGaoPageCrawler.class)
    JSZaiGuaPaiGongGaoPageCrawler JSZaiGuaPaiGongGaoPageCrawler;


    @Scheduled(cron = "0 0 * * * ?")
    public void JSZaiGuaPaiGongGaoPageCrawler() {
        Spider spider = Spider.create(JSZaiGuaPaiGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : JSZaiGuaPaiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JSTingPaiGongGaoPageCrawler.class)
    JSTingPaiGongGaoPageCrawler JSTingPaiGongGaoPageCrawler;


    @Scheduled(cron = "0 0 * * * ?")
    public void JSTingPaiGongGaoPageCrawler() {
        Spider spider = Spider.create(JSTingPaiGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : JSTingPaiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JSFuPaiGongGaoPageCrawler.class)
    JSFuPaiGongGaoPageCrawler JSFuPaiGongGaoPageCrawler;


    @Scheduled(cron = "0 0 * * * ?")
    public void JSFuPaiGongGaoPageCrawler() {
        Spider spider = Spider.create(JSFuPaiGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : JSFuPaiGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }

    @Resource(type = JSTuoGuanGongGaoPageCrawler.class)
    JSTuoGuanGongGaoPageCrawler JSTuoGuanGongGaoPageCrawler;


    @Scheduled(cron = "0 0 * * * ?")
    public void JSTuoGuanGongGaoPageCrawler() {
        Spider spider = Spider.create(JSTuoGuanGongGaoPageCrawler).addPipeline(pipeline);
        for(String startUrl : JSTuoGuanGongGaoPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }


    @Resource(type = JSXianHuoCaiJiJiaPageCrawler.class)
    JSXianHuoCaiJiJiaPageCrawler JSXianHuoCaiJiJiaPageCrawler;


    @Scheduled(cron = "0 0 * * * ?")
    public void JSXianHuoCaiJiJiaPageCrawler() {
        Spider spider = Spider.create(JSXianHuoCaiJiJiaPageCrawler).addPipeline(pipeline);
        for(String startUrl : JSXianHuoCaiJiJiaPageCrawler.getStartUrls()){
            spider.addUrl(startUrl);
        }
        spider.start();
    }



}
