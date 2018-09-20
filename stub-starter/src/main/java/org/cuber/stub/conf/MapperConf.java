package org.cuber.stub.conf;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.cuber.stub.repo.StubDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Configuration
@Component
@Aspect
@Order(1)
public class MapperConf {
    private static Logger logger = LoggerFactory.getLogger(MapperConf.class);

    @Pointcut("execution(* org.cuber.stub.repo.Mapper.create(..))")
    public void idInject() {
    }

    @Before("idInject()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (Objects.nonNull(args) && args.length == 1) {
            if (args[0] instanceof StubDTO) {
                StubDTO stubDTO = (StubDTO) args[0];
                Class tClass = stubDTO.getClass();
                if (StringUtils.isEmpty(stubDTO.getId()) &&
                        SplitTableConf.containsTClass(tClass)) {
                    String id = SplitTableConf.nextId(tClass);
                    stubDTO.setId(id);
                    logger.trace("[{}]自己生成ID:{}", tClass, id);
                }
            }
        }
    }
}
