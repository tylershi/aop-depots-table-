package com.example.table.entity;

import com.example.table.entity.base.BaseEntity;
import com.example.table.response.OrderVo;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:20
 * @Version 1.0
 * @Description
 */
@Data
@Builder
@Table(name = "order")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

  @Id
  private Long orderId;

  private Long userId;

  private double money;

  public static OrderVo toVo(Order order) {
    if (order == null) {
      return null;
    }
    return OrderVo.builder()
        .orderId(order.getOrderId())
        .userId(order.getUserId())
        .money(order.getMoney())
        .build();
  }

}
