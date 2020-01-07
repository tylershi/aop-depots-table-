package com.example.table.dao;

import com.example.table.depots.annotation.Router;
import com.example.table.entity.Order;
import java.util.List;
import org.apache.ibatis.annotations.Param;
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

  @Router
  List<Order> findOrderByOrderIdsV2(@Param("order") Order order,
      @Param("ids") List<Long> ids);

  @Router
  List<Order> findOrderByUserIdV1(@Param("order") Order order,
      @Param("userId") Long userId);

}
