package com.hstmpl.lang;

public interface PrimitiveGetter<K> {

    <T> T getValue(String key, T defValue);

    String getString(K key, String defValue);

    Integer getInt(K key, Integer defValue);

    Float getFloat(K key, Float defValue);

    Double getDouble(K key, Double defValue);

    Long getLong(K key, Long defValue);

    Boolean getBool(K key, Boolean defValue);

    Short getShort(K key, Short defValue);

    default <T> T getValue(String key) {
        return getValue(key, null);
    }

    default String getString(K key) {
        return getString(key, null);
    }

    default Integer getInt(K key) {
        return getInt(key, 0);
    }

    default Float getFloat(K key) {
        return getFloat(key, 0F);
    }

    default Double getDouble(K key) {
        return getDouble(key, 0D);
    }

    default Long getLong(K key) {
        return getLong(key, 0L);
    }

    default Boolean getBool(K key) {
        return getBool(key, false);
    }

    default Short getShort(K key){
        return getShort(key, (short) 0);
    }

}
