package com.example.px_service.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //限制注解使用位置:方法上
@Retention(RetentionPolicy.RUNTIME) //决定注解什么时候有效:运行时
public @interface OperationLog {
}
