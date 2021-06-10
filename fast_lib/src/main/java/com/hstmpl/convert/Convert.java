package com.hstmpl.convert;

import com.hstmpl.util.ClassUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Convert {

    private static final Map<Class<?>, Converter<?>> extendConverter = new HashMap<>();

    public static void addConverter(Class<?> type, Converter<?> converter) {
        extendConverter.put(type, converter);
    }

    public static void removeConverter(Class<?> type) {
        extendConverter.remove(type);
    }

    public static String toString(Object value) {
        return toString(value, "");
    }

    public static String toString(Object value, String defValue) {
        return StringConverter.INSTANCE.convert(value, defValue);
    }

    public static String toStringOrNull(Object value) {
        return toString(value, null);
    }

    public static int toInt(Object value) {
        return toInt(value, 0);
    }

    public static Integer toInt(Object value, Integer defValue) {
        return IntegerConverter.INSTANCE.convert(value, defValue);
    }

    public static Integer toIntOrNull(Object value) {
        return toInt(value, null);
    }

    public static boolean toBool(Object value) {
        return toBool(value, false);
    }

    public static boolean toBool(Object value, Boolean defValue) {
        return BooleanConverter.INSTANCE.convert(value, defValue);
    }

    public static float toFloat(Object value) {
        return toFloat(value, 0f);
    }

    public static Float toFloat(Object value, Float defValue) {
        return FloatConverter.INSTANCE.convert(value, defValue);
    }

    public static Float toFloatOrNull(Object value) {
        return toFloat(value, null);
    }

    public static double toDouble(Object value) {
        return toDouble(value, 0d);
    }

    public static Double toDouble(Object value, Double defValue) {
        return DoubleConverter.INSTANCE.convert(value, defValue);
    }

    public static Double toDoubleOrNull(Object value) {
        return toDouble(value, null);
    }

    public static long toLong(Object value) {
        return toLong(value, 0L);
    }

    public static Long toLong(Object value, Long defValue) {
        return LongConverter.INSTANCE.convert(value, defValue);
    }

    public static Long toLongOrNull(Object value) {
        return toLong(value, null);
    }

    public static short toShort(Object value) {
        return toShort(value, (short) 0);
    }

    public static Short toShort(Object value, Short defValue) {
        return ShortConverter.INSTANCE.convert(value, defValue);
    }

    public static Short toShortOrNull(Object value) {
        return toShort(value, null);
    }

    public static char toChar(Object value) {
        return toChar(value, null);
    }

    public static Character toChar(Object value, Character defValue) {
        return CharacterConverter.INSTANCE.convert(value, defValue);
    }

    public static Character toCharOrNull(Object value) {
        return toChar(value, null);
    }

    public static Date toDateOrNull(Object value) {
        return toDate(value, null);
    }

    public static Date toDate(Object value, Date defValue) {
        return DateConverter.INSTANCE.convert(value, defValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Class<T> type, Object value) {
        if (value == null) {
            return null;
        }
        Converter<T> converter = (Converter<T>) matchConverter(type);
        if (converter == null) {
            return null;
        }
        return converter.convert(value, null);
    }

    // 默认内置转换器
    public static Converter<?> matchConverter(Class<?> type) {
        if (ClassUtils.isString(type)) {
            return StringConverter.INSTANCE;
        } else if (ClassUtils.isInt(type)) {
            return IntegerConverter.INSTANCE;
        } else if (ClassUtils.isLong(type)) {
            return LongConverter.INSTANCE;
        } else if (ClassUtils.isDouble(type)) {
            return DoubleConverter.INSTANCE;
        } else if (ClassUtils.isBool(type)) {
            return BooleanConverter.INSTANCE;
        } else if (ClassUtils.isFloat(type)) {
            return FloatConverter.INSTANCE;
        } else if (ClassUtils.isShort(type)) {
            return ShortConverter.INSTANCE;
        } else if (ClassUtils.isChar(type)) {
            return CharacterConverter.INSTANCE;
        } else if (type == Date.class) {
            return DateConverter.INSTANCE;
        }
        return extendConverter.get(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object value) {
        if (value == null) {
            return null;
        }
        return (T) value;
    }
}
