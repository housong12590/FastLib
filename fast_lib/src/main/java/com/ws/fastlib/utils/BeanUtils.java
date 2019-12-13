package com.ws.fastlib.utils;

import com.ws.fastlib.common.Function;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

public class BeanUtils {

    public static <T> T copyProperties(Object source, Class<T> cls) {
        try {
            T instance = cls.newInstance();
            copyProperties(source, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void copyProperties(Object source, Object target) {
        Field[] targetFields = target.getClass().getDeclaredFields();
        for (Field targetField : targetFields) {
            targetField.setAccessible(true);
            try {
                Field sourceField = source.getClass().getDeclaredField(targetField.getName());
                if (targetField.getType() == sourceField.getType()) {
                    sourceField.setAccessible(true);
                }
                targetField.set(target, sourceField.get(source));
            } catch (Exception ignored) {

            }
        }
    }

    public static <Key, Entity> Map<Key, Entity> toMap(Collection<Entity> list, Function<Entity, Key> mapping) {
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Key, Entity> _map = new HashMap<>();
        for (Entity entity : list) {
            Key key = mapping.apply(entity);
            _map.put(key, entity);
        }
        return _map;
    }

    public static <E, R> List<R> toList(Collection<E> list, Function<E, R> mapping) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        List<R> _list = new ArrayList<>();
        for (E e : list) {
            _list.add(mapping.apply(e));
        }
        return _list;
    }


    public static Map<String, Object> toMap(Object bean) {
        if (bean == null) {
            throw new NullPointerException("bean can not null");
        }
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                Object value = field.get(bean);
                map.put(field.getName(), value);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}