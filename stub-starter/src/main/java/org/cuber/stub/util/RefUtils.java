package org.cuber.stub.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class RefUtils {

    public static <T> T findFieldValue(Object target, String fieldName, Class<T> fieldClass) {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        T result = null;
        if (Objects.nonNull(field) && field.getType().equals(fieldClass)) {
            field.setAccessible(true);
            result = (T) ReflectionUtils.getField(field, target);
        }
        return result;
    }
}
