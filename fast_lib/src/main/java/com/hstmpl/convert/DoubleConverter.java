package com.hstmpl.convert;


import com.blankj.utilcode.util.LogUtils;

public class DoubleConverter implements Converter<Double> {

    public static Converter<Double> INSTANCE = new DoubleConverter();

    @Override
    public Double convert(Object value, Double defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (Exception e) {
                LogUtils.e("convert to double error" + e.getMessage());
            }
        }
        return defValue;
    }
}
