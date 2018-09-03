package org.cuber.stub.conf;

import org.cuber.ids.IdGeneratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties(IdsProperties.class)
public class IdsConf {
    private static Logger logger = LoggerFactory.getLogger(IdsConf.class);

    @Autowired
    private IdsProperties idsProperties;

    @EventListener
    public void ready(ApplicationReadyEvent readyEvent){
        if(Objects.isNull(idsProperties)){
            logger.info("初始化ID生成:{}",idsProperties.getPatterns());
            IdGeneratorFactory.load(idsProperties.getPatterns());
        }
    }
}
