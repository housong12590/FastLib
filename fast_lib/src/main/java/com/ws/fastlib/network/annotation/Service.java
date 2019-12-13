package com.ws.fastlib.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    String baseUrl();

    boolean printLog() default true;

    int timeout() default 30000;

    TimeUnit unit() default TimeUnit.MILLISECONDS;

    Class<?>[] interceptors() default {};

    boolean schedulers() default true;
}
