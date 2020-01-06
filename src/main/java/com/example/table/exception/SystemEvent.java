package com.example.table.exception;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 10:20
 * @Version 1.0
 * @Description 全局异常定义
 */
public enum SystemEvent {

  /**
   * 异常类型及异常码定义
   */
  INVALID_PARAM(400, "接口输入参数不合法"),

  SYSTEM_ERROR(500, "系统异常，操作失败"),

  ORDER_ID_IS_INVALID(1000, "订单id不合法"),

  ROUTING_FIELD_ARGS_ISNULL(10000, "多数据源路由键为空"),

  LOADING_STRATEGY_UN_MATCH(10001, "路由组件加载和配置文件不匹配"),

  FORMAT_TABLE_SUFFIX_ERROR(10002, "格式化表后缀异常"),

  PARAMS_NOT_CONTAINS_ROUTING_FIELD(10003, "无法找到路由字段");

  private Integer id;

  private String details;

  SystemEvent(Integer id, String details) {
    this.id = id;
    this.details = details;
  }


  public Integer getId() {
    return this.id;
  }


  public String getDetails() {
    return details;
  }
}
