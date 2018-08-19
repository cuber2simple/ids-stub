package org.cuber.stub.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JacksonHolder {

    private volatile static ObjectMapper mapper;
    private static Logger logger = LogManager.getLogger(JacksonHolder.class);

    public static ObjectMapper fetchJacksonMapper(){
        if(mapper == null){
            mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        }
        return mapper;
    }

    public static String toJackson(Object obj){
        try {
            return fetchJacksonMapper().writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("转成json出错",e);
        }
        return null;
    }

    public static <T> T instance(String jackson,Class<T> tClass){
        try {
            return fetchJacksonMapper().readValue(jackson,tClass);
        } catch (Exception e) {
            logger.error("转换成对象出错",e);
        }
        return null;
    }
}
