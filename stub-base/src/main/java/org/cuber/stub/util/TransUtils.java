package org.cuber.stub.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.cuber.stub.repo.StubDTO;
import org.cuber.stub.vo.StubVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.Objects;

public class TransUtils {

    private static Logger logger = LoggerFactory.getLogger(TransUtils.class);


    public static <T> T copyP(Object bean, Class<T> voClass) {
        T t = null;
        if (Objects.nonNull(bean) && Objects.nonNull(voClass)) {
            try {
                Map<String, Object> dtoProperties = PropertyUtils.describe(bean);
                MapPropertySource mapPropertySource = new MapPropertySource("dto", dtoProperties);
                t = new Binder(ConfigurationPropertySources.from(mapPropertySource))
                        .bind("", voClass).get();
            } catch (Exception e) {
                logger.error("赋值失败", e);
            }
        }
        return t;
    }


}
