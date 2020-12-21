package com.hstmpl.util;


import com.hstmpl.convert.Convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtils {

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> Map<K, V> of(K key, V value) {
        Map<K, V> map = new HashMap<>(1);
        map.put(key, value);
        return map;
    }


    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> filter(Map<K, V> map, K... keys) {
        if (isEmpty(map)) return new HashMap<>();
        Map<K, V> result = new HashMap<>(map);
        for (K key : keys) {
            result.remove(key);
        }
        return result;
    }
    
    public static void clear(Map<?, ?>... maps) {
        for (Map<?, ?> map : maps) {
            if (!isEmpty(map)) map.clear();
        }
    }

    public static <K, V> V get(Map<?, ?> map, K key) {
        return get(map, key, null);
    }

    public static <K, V> V get(Map<?, ?> map, K key, V defValue) {
        Object value = map.get(key);
        if (value == null) {
            return defValue;
        }
        return Convert.cast(value);
    }

    public static <K> Long getLong(Map<?, ?> map, K key) {
        return getLong(map, key, null);
    }

    public static <K> Long getLong(Map<?, ?> map, K key, Long defValue) {
        return Convert.toLong(map.get(key), defValue);
    }

    public static <K> String getString(Map<?, ?> map, K key) {
        return getString(map, key, null);
    }

    public static <K> String getString(Map<?, ?> map, K key, String defValue) {
        return Convert.toString(map.get(key), defValue);
    }

    public static <K> Integer getInt(Map<?, ?> map, K key) {
        return getInt(map, key, null);
    }

    public static <K> Integer getInt(Map<?, ?> map, K key, Integer defValue) {
        Object value = map.get(key);
        if (value == null) return defValue;
        if (value instanceof Integer) return (Integer) value;
        return Convert.toDouble(value).intValue();
    }

    public static <K> Boolean getBool(Map<?, ?> map, K key) {
        return getBool(map, key, false);
    }

    public static <K> Boolean getBool(Map<?, ?> map, K key, Boolean defValue) {
        Object value = map.get(key);
        if (value == null) return defValue;
        return Convert.toBool(value, defValue);
    }

    public static <K> Double getDouble(Map<?, ?> map, K key) {
        return getDouble(map, key, 0d);
    }

    public static <K> Double getDouble(Map<?, ?> map, K key, Double defValue) {
        return Convert.toDouble(map.get(key), defValue);
    }

    public static <K> Float getFloat(Map<?, ?> map, K key) {
        return getFloat(map, key, 0f);
    }

    public static <K> Float getFloat(Map<?, ?> map, K key, Float defValue) {
        return Convert.toFloat(map.get(key), defValue);
    }

    public static <K> Short getShort(Map<?, ?> map, K key) {
        return getShort(map, key, (short) 0);
    }

    public static <K> Short getShort(Map<?, ?> map, K key, Short defValue) {
        return Convert.toShort(map.get(key), defValue);
    }

    public static <T, K> T getBean(Map<?, ?> map, K key, Class<T> cls) {
        Object value = map.get(key);
        if (value == null) return null;
        if (cls.isInstance(value)) {
            //noinspection unchecked
            return (T) value;
        }
        if (value instanceof Map) {
            return BeanUtils.mapToBean((Map<?, ?>) value, cls);
        }
        return BeanUtils.copyProperties(value, cls);
    }

    @SuppressWarnings("unchecked")
    public static <K> Map<String, Object> getMap(Map<?, ?> map, K key) {
        Object value = map.get(key);
        if (value == null) return null;
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return BeanUtils.toMap(value);
    }

    public static <T> List<T> getList(Map<?, ?> map, String key, Class<T> cls) {
        return getList(map, key, cls, null);
    }

    public static <T, K> List<T> getList(Map<?, ?> map, K key, Class<T> cls, List<T> defValue) {
        Object value = map.get(key);
        if (value == null) return defValue;
        if (value instanceof List) return castList((List<?>) value, cls);
        throw new ClassCastException(value.getClass() + " can not cast List");
    }

    public static <T> T getValue(Map<?, ?> map, String key) {
        return getValue(map, key, null);
    }

    public static <T> T getValue(Map<?, ?> map, String key, T defValue) {
        T value = Expression.of(key).getValue(map);
        if (value == null) {
            return defValue;
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> castList(List<?> list, Class<T> cls) {
        List<T> _list = new ArrayList<>();
        for (Object o : list) {
            if (cls.isInstance(o)) {
                //pass
            } else if (ClassUtils.isPrimitive(cls)) {
                o = Convert.cast(cls, o);
            } else if (o instanceof Map) {
                o = BeanUtils.mapToBean((Map<?, ?>) o, cls);
            } else {
                o = BeanUtils.copyProperties(o, cls);
            }
            _list.add((T) o);
        }
        return _list;
    }
}
