package com.example.table.depots.aspect;


import com.example.table.depots.annotation.Router;
import com.example.table.depots.core.IRouting;
import com.example.table.depots.datasource.MultiDataSourceHolder;
import com.example.table.depots.exception.LoadRoutingStrategyUnMatch;
import com.example.table.depots.exception.ParamsNotContainsRoutingField;
import com.example.table.depots.exception.RoutingFiledArgsIsNull;
import com.example.table.entity.base.BaseEntity;
import com.example.table.exception.BizException;
import com.example.table.exception.SystemEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    //获取指定的路由key
    String routingFiled = getRoutingKey(joinPoint);

    //获取方法入参
    Object[] args = joinPoint.getArgs();

    boolean havingRoutingField = false;

    if (args != null && args.length > 0) {
      for (Object arg : args) {
        String routingFieldValue = BeanUtils.getProperty(arg, routingFiled);
        if (StringUtils.isNotBlank(routingFieldValue)) {
          String dbKey = routing.calDataSourceKey(routingFieldValue);
          String tableIndex = routing.calTableKey(routingFieldValue);
          log.info("路由字段routing_field是:{},路由字段值routing_field_value是:{},"
                  + "选择的数据源db_key是:{},选择的分区表table_key是:{}",
              routingFiled, routingFieldValue, dbKey, tableIndex);
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

  private String getRoutingKey(JoinPoint joinPoint) {
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    Router router = method.getAnnotation(Router.class);
    String routingFiled = router.routingFiled();
    // 如果注解中有routingKey 直接返回
    if (StringUtils.isNotBlank(routingFiled)) {
      return routingFiled;
    }
    // 如果入参是entity对象，且字段有@Id注解，则返回该字段名字
    Object[] args = joinPoint.getArgs();
    if (args != null && args.length > 0) {
      for (Object arg : args) {
        if (arg instanceof BaseEntity) {
          Field[] fields = arg.getClass().getDeclaredFields();
          for (Field field : fields) {
            Id annotation = field.getAnnotation(Id.class);
            if (annotation != null) {
              return field.getName();
            }
          }
        }
      }
    }
    throw new BizException(SystemEvent.ROUTING_FIELD_ARGS_ISNULL);
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
