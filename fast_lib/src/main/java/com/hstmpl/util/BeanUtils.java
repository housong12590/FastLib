package com.hstmpl.util;

import com.hstmpl.convert.Convert;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    public static <T> T copyProperties(Object source, Class<T> cls) {
        try {
            T target = cls.newInstance();
            copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void copyProperties(Object source, Object target) {
        copyProperties0(source, target);
    }

    private static void copyProperties0(Object source, Object target) {
        if (source == null || target == null) return;
        List<Field> dstFields = FieldUtils.getFields(target);
        Map<String, Field> srcFieldMap = FieldUtils.getFieldMap(source);
        for (Field dstField : dstFields) {
            Field srcField = srcFieldMap.get(dstField.getName());
            if (srcField == null) continue;
            Object value = FieldUtils.getValue(srcField, source);
            FieldUtils.setValue(dstField, value, target);
        }
    }

    public static <T> T mapToBean(Map<?, ?> map, Class<T> cls) {
        if (map == null) return null;
        T instance = ClassUtils.newInstance(cls);
        List<Field> fields = FieldUtils.getFields(cls);
        for (Field field : fields) {
            Object value = map.get(field.getName());
            if (value == null) continue;
            FieldUtils.setValue(field, value, instance);
        }
        return instance;
    }

    public static <T> T toBean(Object value, Class<T> cls) {
        if (value == null) return null;
        else if (cls.isInstance(value)) return Convert.cast(value);
        else if (value instanceof Map) {
            return mapToBean((Map<?, ?>) value, cls);
        } else {
            return copyProperties(value, cls);
        }
    }

//    public static <T> T toBean2(Object value, Class<T> cls) {
//        String json = JSON.toJson(value);
//        return JSON.toBean(json, cls);
//    }

    public static Map<String, Object> toMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        toMap(bean, map);
        return map;
    }

    public static void toMap(Object bean, Map<String, Object> map) {
        for (Field field : FieldUtils.getFields(bean)) {
            Object value = FieldUtils.getValue(field, bean);
            map.put(field.getName(), value);
        }
    }

}