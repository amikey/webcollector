package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import com.raozk.modole.AdvisoryNews;
import com.raozk.modole.Announcement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class AdvisoryNewsPipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(AdvisoryNewsPipeline.class);

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    JdbcTemplate jdbcTemplate;

    boolean addCrawed(String url){
        return redisTemplate.opsForSet().add("001:00",url)==1?true:false;
    }

    public void process(ResultItems resultItems, Task task) {
        AdvisoryNews advisoryNews = resultItems.get("advisoryNews");
        if(advisoryNews!=null && addCrawed(resultItems.getRequest().getUrl())){
            saveAdvisoryNews2Redis(advisoryNews);
            saveAdvisoryNews2Mysql(advisoryNews);
        }
    }

    private void saveAdvisoryNews2Redis(AdvisoryNews advisoryNews){
        redisTemplate.opsForList().leftPush("001:02", JSON.toJSONString(advisoryNews));
    }

    private void saveAdvisoryNews2Mysql(AdvisoryNews advisoryNews){
        jdbcTemplate.update("insert into t_advisorynews(title, content, time, exchange, type, st) values(?,?,?,?,?,?)",
                advisoryNews.getTitle(), advisoryNews.getContent(), advisoryNews.getTime(), advisoryNews.getExchange(), advisoryNews.getType(), advisoryNews.getSt());
    }

}
