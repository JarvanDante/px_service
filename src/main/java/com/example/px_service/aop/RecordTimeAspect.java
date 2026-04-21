package com.example.px_service.aop;

import com.example.px_service.interceptor.TokenInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RecordTimeAspect {

    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    private long startTime;

    @Around("execution(* com.example.px_service.service.impl.*.*(..))")
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 执行目标方法
        Object result = pjp.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("方法：" + pjp.getSignature() + "==执行时间: " + executionTime + " ms");
        log.info("方法：" + pjp.getSignature() + "==执行时间: " + executionTime + " ms");
        return result;
    }

}
