package org.cuber.stub.initializer;

import org.cuber.stub.StubConstant;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class AutoZkInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        String appName = configurableApplicationContext.getId();
        PropertyFactory.loadFormZk(appName);
        PropertyFactory.loadFormZk(StubConstant.COMMON);
    }
}
