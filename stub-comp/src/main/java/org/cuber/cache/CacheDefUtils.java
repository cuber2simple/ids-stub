package org.cuber.cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.basic.CacheDef;
import org.cuber.stub.vo.StubConfVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CacheDefUtils {

    public static final String CACHE_FIELD_SPLIT = "_";

    public static final String FIELD_SPLIT_VALUE = ":";

    private static Logger logger = LoggerFactory.getLogger(CacheDefUtils.class);

    public static <T extends StubConfVO> List<String> findKeys(T cacheIns, CacheDef cacheDef) {
        Set<List<String>> fields = cacheDef.getFieldKeys();
        List<String> keys = null;
        if (fields != null) {
            keys = fields.stream()
                    .map(list -> key(cacheIns, list))
                    .collect(Collectors.toList());
        }
        return keys;
    }

    public static <T> String key(T cacheIns, List<String> fields) {
        String values = null;
        if (CollectionUtils.isNotEmpty(fields)) {
            List<String> fieldValues = fields.stream()
                    .map(field -> field2Key(cacheIns, field))
                    .collect(Collectors.toList());
            values = StringUtils.join(fieldValues, CACHE_FIELD_SPLIT);
        }
        return values;
    }

    public static <T> String field2Key(T cacheIns, String fieldName) {
        String key = fieldName + FIELD_SPLIT_VALUE + fieldValue(cacheIns, fieldName);
        return key;
    }

    public static <T> Object fieldValue(T cacheIns, String fieldName) {
        Field field = ReflectionUtils.findField(cacheIns.getClass(), fieldName);
        ReflectionUtils.makeAccessible(field);
        return ReflectionUtils.getField(field, cacheIns);
    }

    public static <T> boolean isKey(T searchIns, List<String> fields) {
        return fields.stream().allMatch(field -> Objects.nonNull(fieldValue(searchIns, field)));
    }

    public static <T extends StubConfVO> T makeSearchIns(String key, CacheDef def, Class<T> tClass) {
        T search = null;
        String[] fieldValuePairs = StringUtils.split(key, CacheDefUtils.CACHE_FIELD_SPLIT);
        if (fieldValuePairs != null && fieldValuePairs.length > 0) {
            try {
                search = tClass.newInstance();
                for (int i = 0; i < fieldValuePairs.length; i++) {
                    String fieldAndValue = fieldValuePairs[i];
                    String[] pair = StringUtils.split(fieldAndValue, CacheDefUtils.FIELD_SPLIT_VALUE);
                    if (pair != null && pair.length == 2) {
                        Field field = ReflectionUtils.findField(tClass, pair[0]);
                        ReflectionUtils.makeAccessible(field);

                        ReflectionUtils.setField(field, search, pair[1]);
                    }
                }
            } catch (Exception e) {
                logger.error("转换搜索实体报错", e);
            }
        }
        return search;
    }
}
