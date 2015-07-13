package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class MobilePipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(MobilePipeline.class);

    @Resource(name="mobileRedisTemplate")
    RedisTemplate<String, String> redisTemplate;

    public void process(ResultItems resultItems, Task task) {
        Map<String, Map<String, String>> mobileMap = resultItems.get("mobile");
        if(mobileMap == null || mobileMap.isEmpty()){
            return;
        }

        for(Map.Entry entry : mobileMap.entrySet()){
            redisTemplate.opsForValue().set("013_1:"+entry.getKey(), JSON.toJSONString(entry.getValue()));
        }
    }

}
