package org.cuber.stub.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.cuber.ids.IdGenerator;
import org.cuber.ids.IdGeneratorFactory;
import org.cuber.stub.repo.StubDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Configuration
@Component
@Aspect
@Order(1)
public class MapperAop {
    private static Logger logger = LogManager.getLogger(MapperAop.class);

    @Pointcut("execution(* org.cuber.stub.repo.Mapper.create(..))")
    public void insertPoint(){}

    @Before("insertPoint()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if(Objects.nonNull(args) && args.length == 1){
            if(args[0] instanceof StubDTO){
                StubDTO stubDTO = (StubDTO) args[0];
                IdGenerator idGenerator = IdGeneratorFactory.findByClass(args[0].getClass());
                if(idGenerator != null){
                    stubDTO.setId(idGenerator.nextId());
                }
            }
        }
    }
}
