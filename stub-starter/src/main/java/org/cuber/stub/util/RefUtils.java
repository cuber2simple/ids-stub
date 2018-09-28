package org.cuber.stub.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    public static Method findFirstNameMatch(Class target, String methodName) {
        Method[] methods = target.getMethods();
        Method result = null;
        if(methods != null && methods.length > 0){
            for(int i = 0; i < methods.length; i++){
                if(methods[i].getName().equals(methodName)){
                    result = methods[i];
                    break;
                }
            }
        }
        return result;
    }
}
