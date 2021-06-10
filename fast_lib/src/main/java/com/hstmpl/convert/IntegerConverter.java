package com.hstmpl.convert;


import com.blankj.utilcode.util.LogUtils;

public class IntegerConverter implements Converter<Integer> {

    public static final int TRUE_VAL = 1;
    public static final int FALSE_VAL = 0;

    public static Converter<Integer> INSTANCE = new IntegerConverter();

    @Override
    public Integer convert(Object value, Integer defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof Boolean) {
            return ((boolean) value) ? TRUE_VAL : FALSE_VAL;
        }
        if (value instanceof String) {
            try {
                double tmpValue = Double.parseDouble((String) value);
                return (int) tmpValue;
            } catch (Exception e) {
                LogUtils.e("convert to int error, " + e.getMessage());
            }
        }
        return defValue;
    }
}
