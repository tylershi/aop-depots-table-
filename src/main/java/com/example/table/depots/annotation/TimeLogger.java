package com.example.table.depots.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * @Author tyler.shi
 * @Date 2020/1/7 11:06
 * @Version 1.0
 * @Description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeLogger {

  /**
   * 自定义日志名称
   */
  @AliasFor("value")
  String[] name() default "";

  @AliasFor("name")
  String[] value() default "";

  String formatPattern() default "";

}
