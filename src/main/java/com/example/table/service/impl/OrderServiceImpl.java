package com.example.table.service.impl;

import com.example.table.dao.OrderMapper;
import com.example.table.entity.Order;
import com.example.table.request.OrderRequest;
import com.example.table.response.OrderVo;
import com.example.table.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 11:16
 * @Version 1.0
 * @Description
 */
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderMapper orderMapper;

  @Autowired
  public OrderServiceImpl(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  @Override
  public void saveOrder(OrderRequest request) {
    // 实际情况下，这里的主键应该服务端生成，而不是接口传入 雪花算法或者redis获取
    Order order = Order.builder()
        .orderId(request.getOrderId())
        .userId(request.getUserId())
        .money(request.getMoney())
        .build();
    orderMapper.insertOrder(order);
  }

  @Override
  public void deleteOrder(Long id) {
    Order order = Order.builder().orderId(id).build();
    orderMapper.deleteOrder(order);
  }

  @Override
  public void updateOrder(OrderRequest request) {
    Order order = Order.builder()
        .orderId(request.getOrderId())
        .userId(request.getUserId())
        .money(request.getMoney())
        .build();
    orderMapper.updateOrder(order);
  }

  @Override
  public OrderVo findOrder(Long id) {
    Order order = Order.builder().orderId(id).build();
    return Order.toVo(orderMapper.findOrder(order));
  }

  @Override
  public List<OrderVo> findOrderByOrderIds(List<Long> orderIds) {
    return null;
  }

  @Override
  public List<OrderVo> findOrderByUserId(Long userId) {
    return null;
  }

  @Override
  public List<OrderVo> findOrderByUserIds(List<Long> userIds) {
    return null;
  }
}
