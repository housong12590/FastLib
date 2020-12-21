package com.hstmpl.convert;

public class Convert {

    public static String toString(Object value) {
        return toString(value, null);
    }

    public static String toString(Object value, String defValue) {
        return StringConverter.INSTANCE.convert(value, defValue);
    }

    public static Integer toInt(Object value) {
        return toInt(value, 0);
    }

    public static Integer toInt(Object value, Integer defValue) {
        return IntegerConverter.INSTANCE.convert(value, defValue);
    }

    public static Boolean toBool(Object value) {
        return toBool(value, false);
    }

    public static Boolean toBool(Object value, Boolean defValue) {
        return BooleanConverter.INSTANCE.convert(value, defValue);
    }

    public static Float toFloat(Object value) {
        return toFloat(value, 0f);
    }

    public static Float toFloat(Object value, Float defValue) {
        return FloatConverter.INSTANCE.convert(value, defValue);
    }

    public static Double toDouble(Object value) {
        return toDouble(value, 0d);
    }

    public static Double toDouble(Object value, Double defValue) {
        return DoubleConverter.INSTANCE.convert(value, defValue);
    }

    public static Long toLong(Object value) {
        return toLong(value, 0L);
    }

    public static Long toLong(Object value, Long defValue) {
        return LongConverter.INSTANCE.convert(value, defValue);
    }

    public static Short toShort(Object value) {
        return toShort(value, (short) 0);
    }

    public static Short toShort(Object value, Short defValue) {
        return ShortConverter.INSTANCE.convert(value, defValue);
    }

    public static Character toChar(Object value) {
        return toChar(value, null);
    }

    public static Character toChar(Object value, Character defValue) {
        return CharacterConverter.INSTANCE.convert(value, defValue);
    }

    public static <T> T cast(Class<T> type, Object value) {
        if (value == null) {
            return null;
        }
        ConverterRegistry registry = ConverterRegistry.getInstance();
        Converter<T> converter = registry.getConverter(type);
        if (converter == null) {
            return null;
        }
        return converter.convert(value, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object value) {
        if (value == null) {
            return null;
        }
        return (T) value;
    }
}
