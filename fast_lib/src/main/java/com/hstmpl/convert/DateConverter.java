package com.hstmpl.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换器
 * 数字类型: 只能转换10位或13位时间戳
 * string : 只能转yyyy-MM-dd或yyyy-MM-dd HH:mm:ss这两种格式时间
 */
public class DateConverter implements Converter<Date> {

    public static Converter<Date> INSTANCE = new DateConverter();

    @Override
    public Date convert(Object value, Date defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Date) {
            return (Date) value;
        }

        if (value instanceof Number) {
            long tempValue = ((Number) value).longValue();
            return numberToDate(tempValue);
        }

        if (value instanceof String) {
            String tempValue = (String) value;
            return stringToDate(tempValue, defValue);
        }
        return defValue;
    }

    private Date numberToDate(long value) {
        if (value < 1e10) {
            value *= 1000;
        }
        return new Date(value);
    }

    private Date stringToDate(String value, Date defValue) {
        int strLen = value.length();

        if (strLen == 10 || strLen == 13) {
            try {
                long aLong = Long.parseLong(value);
                return numberToDate(aLong);
            } catch (Exception ignored) {

            }
        }

        String layout = null;
        switch (strLen) {
            case 10:
                layout = "yyyy-MM-dd";
                break;
            case 19:
                layout = "yyyy-MM-dd HH:mm:ss";
                break;
        }
        if (layout != null) {
            try {
                return new SimpleDateFormat(layout, Locale.CHINA).parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }
}
