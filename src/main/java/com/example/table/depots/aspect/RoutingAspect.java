package com.example.table.depots.aspect;


import com.example.table.depots.annotation.Router;
import com.example.table.depots.core.IRouting;
import com.example.table.depots.datasource.MultiDataSourceHolder;
import com.example.table.depots.exception.LoadRoutingStrategyUnMatch;
import com.example.table.depots.exception.ParamsNotContainsRoutingField;
import com.example.table.depots.exception.RoutingFiledArgsIsNull;
import com.example.table.entity.base.BaseEntity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:22
 * @Version 1.0
 * @Description
 */
@Component
@Aspect
@Slf4j
public class RoutingAspect {

  private final IRouting routing;

  @Autowired
  public RoutingAspect(IRouting routing) {
    this.routing = routing;
  }

  @Pointcut("@annotation(com.example.table.depots.annotation.Router)")
  public void pointCut() {
  }

  @Before("pointCut()")
  public void before(JoinPoint joinPoint)
      throws LoadRoutingStrategyUnMatch, RoutingFiledArgsIsNull, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    //获取方法调用名称
    Method method = getInvokeMethod(joinPoint);

    //获取方法指定的注解
    Router router = method.getAnnotation(Router.class);
    //获取指定的路由key
    String routingFiled = router.routingFiled();

    //获取方法入参
    Object[] args = joinPoint.getArgs();

    boolean havingRoutingField = false;

    if (args != null && args.length > 0) {
      for (Object arg : args) {
        String routingFieldValue = BeanUtils.getProperty(arg, routingFiled);
        if (!StringUtils.isEmpty(routingFieldValue)) {
          String dbKey = routing.calDataSourceKey(routingFieldValue);
          String tableIndex = routing.calTableKey(routingFieldValue);
          log.info("选择的db_key是:{},table_Key是:{}", dbKey, tableIndex);
          if (arg instanceof BaseEntity) {
            ((BaseEntity) arg).setTableSuffix(tableIndex);
            ((BaseEntity) arg).setRealTableName(((BaseEntity) arg).getRealTableName());
          }
          havingRoutingField = true;
          break;
        }
      }

      //判断入参中没有路由字段
      if (!havingRoutingField) {
        log.warn("入参{}中没有包含路由字段:{}", args, routingFiled);
        throw new ParamsNotContainsRoutingField();
      }
    }

  }

  private Method getInvokeMethod(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    return methodSignature.getMethod();
  }

  /**
   * @Description 清除线程缓存
   */
  @After("pointCut()")
  public void methodAfter() {
    MultiDataSourceHolder.clearDataSourceKey();
    MultiDataSourceHolder.clearTableIndex();
  }
}
