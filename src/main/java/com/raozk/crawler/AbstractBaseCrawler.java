package com.raozk.crawler;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rzk on 15-6-16.
 */
public abstract class AbstractBaseCrawler implements BaseCrawler{

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public boolean crawed(String exchange, String type, String url){
        return redisTemplate.opsForSet().isMember("001:00:"+exchange+":"+type,url);
    }

}
