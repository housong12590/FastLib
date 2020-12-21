package com.hstmpl.convert;

public interface Converter<T> {

    T convert(Object value, T defValue);
}
