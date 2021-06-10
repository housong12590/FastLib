package com.hstmpl.convert;


import com.blankj.utilcode.util.LogUtils;

public class FloatConverter implements Converter<Float> {

    public static Converter<Float> INSTANCE = new FloatConverter();

    @Override
    public Float convert(Object value, Float defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        if (value instanceof String) {
            try {
                return Float.parseFloat((String) value);
            } catch (Exception e) {
                LogUtils.e("convert to float error, " + e.getMessage());
            }
        }
        return defValue;
    }
}
