package org.cuber.stub.conf;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.cuber.stub.interceptor.MybatisTableSplitInterceptor;
import org.cuber.stub.interceptor.TrapParamInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnBean({DataSource.class})
@EnableConfigurationProperties({MybatisProperties.class})
@AutoConfigureAfter({MybatisAutoConfiguration.class})
@ConfigurationProperties("mybatis.page")
public class MybatisConf extends Properties {
    @Autowired(required = false)
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void addPlugin() {
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        PageInterceptor pageInterceptor = new PageInterceptor();
        configuration.setLogImpl(Log4j2Impl.class);
        pageInterceptor.setProperties(this);
        TrapParamInterceptor paramInterceptor = new TrapParamInterceptor();
        MybatisTableSplitInterceptor interceptor = new MybatisTableSplitInterceptor();
        configuration.addInterceptor(paramInterceptor);
        configuration.addInterceptor(pageInterceptor);
        configuration.addInterceptor(interceptor);
    }

}
