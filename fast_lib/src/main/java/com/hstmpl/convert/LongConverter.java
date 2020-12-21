package com.hstmpl.convert;


import com.hstmpl.util.LogUtils;

import java.util.Date;

public class LongConverter implements Converter<Long> {

    public static Converter<Long> INSTANCE = new LongConverter();

    @Override
    public Long convert(Object value, Long defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (Exception e) {
                LogUtils.e("convert to long error, " + e.getMessage());
                return defValue;
            }
        }
        if (value instanceof Date) {
            return ((Date) value).getTime() / 1000;
        }
        return defValue;
    }
}
