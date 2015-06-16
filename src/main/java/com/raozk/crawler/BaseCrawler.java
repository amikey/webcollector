package com.raozk.crawler;

import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
public interface BaseCrawler extends PageProcessor {

    List<String> getStartUrls();

}
