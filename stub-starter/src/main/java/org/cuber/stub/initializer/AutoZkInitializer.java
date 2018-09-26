package org.cuber.stub.initializer;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class AutoZkInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        String appName = configurableApplicationContext.getId();
        System.setProperty(StubConstant.APP_NAME, appName);
        PropertyFactory.loadFormZk(appName);
        PropertyFactory.loadFormZk(StubConstant.COMMON);
        String center = configurableApplicationContext.getEnvironment().getProperty(StubConstant.CENTER);
        if(StringUtils.isNotEmpty(center)){
            System.setProperty(StubConstant.CENTER, center);
        }

    }
}
