package com.example.px_service.aop;

import com.example.px_service.common.context.UserContext;
import com.example.px_service.domain.OperateLog;
import com.example.px_service.mapper.OperateLogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.example.px_service.anno.OperationLog)")
    public Object logOperation(ProceedingJoinPoint pjp) throws Throwable {

        long startTime = System.currentTimeMillis();
        //获取方法签名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        //执行目标方法
        Object result = pjp.proceed();

        //计算耗时
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;

        //构建日志实体
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(Math.toIntExact(UserContext.getUserId()));
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setClassName(pjp.getTarget().getClass().getName());
        operateLog.setMethodName(method.getName());
        operateLog.setMethodParams(Arrays.toString(pjp.getArgs()));
        operateLog.setReturnValue(result != null ? result.toString() : "void");
        operateLog.setCostTime(costTime);

        //保存日志
        operateLogMapper.insert(operateLog);
        
        return result;
    }

}
