package com.hstmpl.convert;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConverterRegistry {

    private Map<Type, Converter<?>> converterMap;

    private static class SingletonHolder {
        private static final ConverterRegistry INSTANCE = new ConverterRegistry();
    }

    public static ConverterRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> Converter<T> getConverter(Type type) {
        Converter<?> converter = converterMap.get(type);
        if (converter != null) {
            return (Converter<T>) converter;
        }
        return null;
    }

    public <T> T convert(Type type, Object value, T defValue) {
        Converter<T> converter = getConverter(type);
        return converter.convert(value, defValue);
    }

    public ConverterRegistry() {
        converterMap = new HashMap<>();
        defaultConverter();
    }

    public void defaultConverter() {
        converterMap.put(Integer.class, IntegerConverter.INSTANCE);
        converterMap.put(Double.class, DoubleConverter.INSTANCE);
        converterMap.put(Float.class, FloatConverter.INSTANCE);
        converterMap.put(Boolean.class, BooleanConverter.INSTANCE);
        converterMap.put(Long.class, LongConverter.INSTANCE);
        converterMap.put(Short.class, ShortConverter.INSTANCE);
        converterMap.put(Character.class, CharacterConverter.INSTANCE);
        converterMap.put(String.class, StringConverter.INSTANCE);
    }
}
