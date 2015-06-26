package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class RedisZXPipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(RedisZXPipeline.class);

    @Resource
    RedisTemplate<String, String> redisTemplate;

    boolean addCrawed(String url){
        return redisTemplate.opsForSet().add("001:00",url)==1?true:false;
    }

    public void process(ResultItems resultItems, Task task) {
        if(resultItems.getAll()!=null && resultItems.getAll().size()>0 && addCrawed(resultItems.getRequest().getUrl())){
            redisTemplate.opsForList().leftPush("001:02", JSON.toJSONString(resultItems.getAll()));
        }
    }

}
