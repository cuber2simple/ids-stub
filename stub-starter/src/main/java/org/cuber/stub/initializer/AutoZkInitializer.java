package org.cuber.stub.initializer;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Objects;

public class AutoZkInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        String appName = configurableApplicationContext.getId();
        System.setProperty(StubConstant.APP_NAME, appName);
        ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
        MapPropertySource appPropertySource = PropertyFactory.loadFormZk(appName);
        if (Objects.nonNull(appPropertySource)) {
            configurableEnvironment.getPropertySources().addLast(appPropertySource);
        }
        MapPropertySource commonPropertySource = PropertyFactory.loadFormZk(StubConstant.COMMON);
        if (Objects.nonNull(commonPropertySource)) {
            configurableEnvironment.getPropertySources().addLast(commonPropertySource);
        }
        String center = configurableApplicationContext.getEnvironment().getProperty(StubConstant.CENTER);
        if (StringUtils.isNotEmpty(center)) {
            System.setProperty(StubConstant.CENTER, center);
        }

    }
}
