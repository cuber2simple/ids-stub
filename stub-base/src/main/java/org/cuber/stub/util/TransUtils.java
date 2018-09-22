package org.cuber.stub.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.MapPropertySource;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class TransUtils {

    private static Logger logger = LoggerFactory.getLogger(TransUtils.class);

    public static <T> T copyP(Object bean, Class<T> tClass, String... ignoreProperties) {
        T t = null;
        if (Objects.nonNull(bean) && Objects.nonNull(tClass)) {
            try {
                Map<String, Object> dtoProperties = PropertyUtils.describe(bean);
                if (ignoreProperties != null && ignoreProperties.length > 0) {
                    Arrays.stream(ignoreProperties).forEach(property -> dtoProperties.remove(property));
                }
                MapPropertySource mapPropertySource = new MapPropertySource("tmp", dtoProperties);
                t = new Binder(ConfigurationPropertySources.from(mapPropertySource))
                        .bind("", tClass).get();
            } catch (Exception e) {
                logger.error("赋值失败", e);
            }
        }
        return t;
    }

}
