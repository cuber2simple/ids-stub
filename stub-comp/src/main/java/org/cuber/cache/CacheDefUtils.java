package org.cuber.cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.vo.StubConfVO;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CacheDefUtils {

    public static final String CACHE_FIELD_SPLIT = "_";

    public static <T extends StubConfVO> List<String> findKeys(T cacheIns, CacheDef<T> cacheDef) {
        Set<List<String>> fields = cacheDef.getFieldKeys();
        List<String> keys = null;
        if (fields != null) {
            keys = fields.stream()
                    .map(list -> key(cacheIns, list))
                    .collect(Collectors.toList());
        }
        return keys;
    }

    private static <T> String key(T cacheIns, List<String> fields) {
        String values = null;
        if (CollectionUtils.isNotEmpty(fields)) {
            List<String> fieldValues = fields.stream()
                    .map(field -> fieldToString(cacheIns, field))
                    .collect(Collectors.toList());
            values = StringUtils.join(fieldValues, CACHE_FIELD_SPLIT);
        }
        return values;
    }

    private static <T> String fieldToString(T cacheIns, String fieldName) {
        Field field = ReflectionUtils.findField(cacheIns.getClass(), fieldName);
        return Objects.toString(ReflectionUtils.getField(field, cacheIns));
    }

}
