package com.hstmpl.convert;


import com.blankj.utilcode.util.LogUtils;

import static com.hstmpl.convert.IntegerConverter.FALSE_VAL;
import static com.hstmpl.convert.IntegerConverter.TRUE_VAL;

public class ShortConverter implements Converter<Short> {

    public static Converter<Short> INSTANCE = new ShortConverter();

    @Override
    public Short convert(Object value, Short defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }
        if (value instanceof Boolean) {
            return (boolean) value ? (short) TRUE_VAL : (short) FALSE_VAL;
        }
        if (value instanceof String) {
            try {
                return Short.parseShort((String) value);
            } catch (Exception e) {
                LogUtils.e("convert to short error, " + e.getMessage());
            }
        }
        return defValue;
    }
}
