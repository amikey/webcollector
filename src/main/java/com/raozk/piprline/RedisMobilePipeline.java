package com.raozk.piprline;

import com.alibaba.fastjson.JSON;
import org.jsoup.helper.StringUtil;
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
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rzk on 15-6-16.
 */
@Component
public class RedisMobilePipeline implements Pipeline {

    private static Logger logger = LoggerFactory.getLogger(RedisMobilePipeline.class);

    @Resource(name="mobileRedisTemplate")
    RedisTemplate<String, String> redisTemplate;

    public void process(ResultItems resultItems, Task task) {
        List<String> contents = resultItems.get("content");
        if(contents != null && contents.size()>0){
            for(String content : contents){
                if(StringUtils.hasText(content)){
                    if(content.indexOf("手机")>0){
                        String[] ss = content.split("手机");
                        if(ss.length>1){
                            String mobile = ss[1];
                            mobile = mobile.trim();
                            if(mobile.startsWith(":") || mobile.startsWith("：")){
                                mobile = mobile.substring(1).trim().substring(0 ,11);
                            }else{
                                mobile = mobile.substring(0, 11);
                            }
                            if(isMobileNO(mobile)){
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("value", content);
                                redisTemplate.opsForValue().set("013:"+mobile, JSON.toJSONString(map));
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
