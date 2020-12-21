package com.hstmpl.aspectj;


import com.hstmpl.aspectj.annotation.Async;
import com.hstmpl.util.ThreadPools;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class AsyncAspect {


    @Pointcut("execution(@com.ws.fastlib.aspectj.annotation.Async * *(..))")
    public void asyncMethod() {
    }

    @Around("asyncMethod()")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(Async.class)) {
            return;
        }

        ThreadPools.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throw new RuntimeException(throwable.getMessage());
                }
            }
        });
    }
}
