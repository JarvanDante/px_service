package com.example.px_service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(10)
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* com.example.px_service.service.impl.*.*(..))")
    private void pointcut() {
    }

    //前置通知 - 目标方法执行前执行
    @Before("pointcut()")
    public void before() {
        log.info("before......");
    }

    //环绕通知 - 目标方法执行前/后执行
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around--before......");

        Object result = pjp.proceed();

        log.info("around--bafter......");

        return result;
    }

    //后置通知 - 目标方法执行后执行
    @After("pointcut()")
    public void after() {
        log.info("after......");
    }

    //返回后通知
    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("afterReturning......");
    }

    //异常后通知
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("afterThrowing......");
    }

}
