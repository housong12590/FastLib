package com.hstmpl.convert;

public class StringConverter implements Converter<String> {

    public static Converter<String> INSTANCE = new StringConverter();

    @Override
    public String convert(Object value, String defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }
}
