package com.hstmpl.convert;

public class BooleanConverter implements Converter<Boolean> {

    public static Converter<Boolean> INSTANCE = new BooleanConverter();

    @Override
    public Boolean convert(Object value, Boolean defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue() > 0;
        }
        if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return defValue;
    }
}
