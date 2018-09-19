package org.cuber.stub.conf;

import org.apache.commons.collections4.CollectionUtils;
import org.cuber.basic.facade.bridge.BizTableDefBridge;
import org.cuber.stub.StubConstant;
import org.cuber.stub.basic.BizTableDef;
import org.cuber.stub.rpc.Req;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.util.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Objects;

@Configuration
public class SplitTableConf {
    private static Logger logger = LoggerFactory.getLogger(SplitTableConf.class);
    @Autowired
    private ApplicationContext applicationContext;



    @EventListener
    public void cachePrepare(ApplicationReadyEvent applicationReadyEvent) {
        if (!StubConstant.BASIC_SERVICE.equals(applicationContext.getApplicationName())) {
            BizTableDefBridge bizTableDefBridge = applicationContext.getBean(BizTableDefBridge.class);
            if (Objects.nonNull(bizTableDefBridge)) {
                Resp<List<BizTableDef>> resp = bizTableDefBridge.loadCacheByAppName(new Req<>(applicationContext.getApplicationName()));
                if(RpcUtils.isSuccess(resp)){
                    List<BizTableDef> result = resp.getResult();
                    if(CollectionUtils.isNotEmpty(result)){
                        result.forEach(bizTableDef -> {

                        });
                    }
                }
            } else {
                logger.warn(StubConstant.WITHOUT_BASIC_WARN);
            }
        }
    }
}
