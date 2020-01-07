package com.example.table.depots.aspect;

import com.example.table.depots.annotation.TimeLogger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @Author tyler.shi
 * @Date 2020/1/7 11:07
 * @Version 1.0
 * @Description
 */
@Slf4j
@Aspect
@Component
public class TimeLoggerAspect {

  private static final String FORMAT = "执行耗时：{} ms";

  /**
   * 定义切点
   */
  @Pointcut("@annotation(com.example.table.depots.annotation.TimeLogger)")
  public void point() {
  }

  @Around(value = "point()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    TimeLogger annotation = signature.getMethod().getAnnotation(TimeLogger.class);
    if (annotation != null) {
      String argument = String.format(annotation.formatPattern(), (Object) annotation.name());
      long start = System.currentTimeMillis();
      Object proceed = joinPoint.proceed();
      long end = System.currentTimeMillis();
      log.info(argument + FORMAT, end - start);
      return proceed;
    }
    return new Object();
  }
}
