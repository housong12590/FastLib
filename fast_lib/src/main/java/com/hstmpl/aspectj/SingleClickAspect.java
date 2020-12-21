package com.hstmpl.aspectj;

import android.util.Log;
import android.view.View;

import com.hstmpl.aspectj.annotation.SingleClick;
import com.hstmpl.util.XClickUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class SingleClickAspect {
    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 注意：这里me.baron.test.annotation.SingleClick需要替换成
     * 你自己项目中SingleClick这个类的全路径哦
     */
    @Pointcut("execution(@com.ws.fastlib.aspectj.annotation.SingleClick * *(..))")
    public void methodAnnotated() {
    }

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的参数
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }

        // 取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        if (view == null) {
            Log.e("Aspect", String.format("@SingleClick 注解的方法%s缺少view参数", method.getName()));
            return;
        }

        if (!method.isAnnotationPresent(SingleClick.class)) {
            return;
        }
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        String clsName = view.getContext().getClass().getName();
        String viewName = view.getClass().getSimpleName();
        int viewId = view.getId();
        String key = String.format("%s$%s$%s", clsName, viewName, viewId);
        // 判断是否快速点击
        if (!XClickUtil.isFastDoubleClick(key, singleClick.value())) {
            // 不是快速点击，执行原方法
            joinPoint.proceed();
        }
    }
}
