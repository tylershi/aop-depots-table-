package com.example.table.dao;

import com.example.table.depots.annotation.Router;
import com.example.table.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:19
 * @Version 1.0
 * @Description
 */
@Repository
public interface OrderMapper {

  @Router(routingFiled = "orderId")
  void insertOrder(Order order);

  @Router(routingFiled = "orderId")
  void deleteOrder(Order order);

  @Router(routingFiled = "orderId")
  void updateOrder(Order order);

  @Router
  Order findOrder(Order order);

}
