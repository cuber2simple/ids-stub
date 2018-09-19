package org.cuber.stub.conf;

import org.cuber.basic.facade.bridge.BizTableDefBridge;
import org.cuber.stub.StubConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Objects;

@Configuration
public class SplitTableConf {
    private static Logger logger = LoggerFactory.getLogger(SplitTableConf.class);
    @Autowired
    private ApplicationContext applicationContext;

    @EventListener
    public void cachePrepare(ApplicationReadyEvent applicationReadyEvent) {
        if(!StubConstant.BASIC_SERVICE.equals(applicationContext.getApplicationName())){
            BizTableDefBridge bizTableDefBridge = applicationContext.getBean(BizTableDefBridge.class);
            if(Objects.nonNull(bizTableDefBridge)){
                
            }else{
                logger.warn(StubConstant.WITHOUT_BASIC_WARN);
            }
        }


    }
}
