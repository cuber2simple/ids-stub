package org.cuber.stub.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.cuber.stub.conf.SplitTableConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class TrapParamInterceptor implements Interceptor {

    private static final ThreadLocal<Object> paramLocal = new ThreadLocal<>();
    private static Logger logger = LoggerFactory.getLogger(MybatisTableSplitInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if(args != null && args.length >= 4){
            MappedStatement mappedStatement = (MappedStatement)args[0];
            Object parameter = args[1];
            if(MybatisTableSplitInterceptor.isSplit(mappedStatement)){
                paramLocal.set(parameter);
            }
        }
        return invocation.proceed();
    }

    protected static Object findParam(){
        return paramLocal.get();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public static StandardEvaluationContext build(){
        try{
            StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(TrapParamInterceptor.findParam());
            DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy_MM_dd");
            DateTimeFormatter yyyy_MM = DateTimeFormatter.ofPattern("yyyy_MM");
            standardEvaluationContext.setVariable("yyyy_MM_dd", yyyy_MM_dd);
            standardEvaluationContext.setVariable("yyyy_MM", yyyy_MM);
            standardEvaluationContext.registerFunction("findIdTime",
                    SplitTableConf.class.getDeclaredMethod("findIdTime", new Class[]{String.class,String.class}));
            standardEvaluationContext.registerFunction("findIdTimeC",
                    SplitTableConf.class.getDeclaredMethod("findIdTime", new Class[]{Class.class,String.class}));
            standardEvaluationContext.registerFunction("ldt_parse",
                    LocalDateTime.class.getDeclaredMethod("parse", CharSequence.class, DateTimeFormatter.class));
            return standardEvaluationContext;
        }catch (Exception e){
            logger.error("生成IP执行出错");
        }
        return null;
    }
}