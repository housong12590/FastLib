package com.hstmpl.util;


import com.hstmpl.convert.Convert;

import java.util.HashMap;
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
        return Convert.toInt(value, defValue);
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

    public static <T> T getValue(Map<?, ?> map, String key) {
        return getValue(map, key, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Map<?, ?> map, String key, T defValue) {
        T value = Expression.of(key).getValue(map);
        if (value == null) {
            return defValue;
        }
        if (defValue == null) {
            return value;
        }
        Class<?> targetClass = defValue.getClass();
        if (targetClass.isInstance(value)) {
            return value;
        }
        Object newValue = Convert.cast(targetClass, value);
        if (newValue != null) {
            return (T) newValue;
        }
        return value;
    }
}

