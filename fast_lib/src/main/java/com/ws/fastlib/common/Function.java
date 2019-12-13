package com.ws.fastlib.common;

@FunctionalInterface
public interface Function<T, R> {

    R apply(T t);

}
