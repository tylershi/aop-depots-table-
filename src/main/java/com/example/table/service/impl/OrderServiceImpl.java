package com.example.table.service.impl;

import com.example.table.dao.OrderMapper;
import com.example.table.entity.Order;
import com.example.table.request.OrderRequest;
import com.example.table.response.OrderVo;
import com.example.table.service.OrderService;
import java.util.ArrayList;
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

  /**
   * @Description 入参长度是10.需要执行10次sql查询，如果长度是100就要查询100
   */
  @Override
  public List<OrderVo> findOrderByOrderIds(List<Long> orderIds) {
    List<OrderVo> orders = new ArrayList<>();
    for (Long id : orderIds) {
      Order order = Order.builder().orderId(id).build();
      Order exist = orderMapper.findOrder(order);
      if (exist != null) {
        orders.add(Order.toVo(exist));
      }
    }
    return orders;
  }


  /**
   * @Description 3个库，每个库5张表，共15个分区，每个分区扫描一次也可以得到全部结果集，扫描15次就可以
   */
  @Override
  public List<OrderVo> findOrderByOrderIdsV2(List<Long> orderIds) {
    List<OrderVo> orders = new ArrayList<>();

    return null;
  }

  /**
   * @Description userId不是路由字段，怎么击中数据呢？
   * @Description ES OR Redis
   * @Description userId 映射到 orderIds
   * @Description orderIds 路由
   */
  @Override
  public List<OrderVo> findOrderByUserId(Long userId) {
    return null;
  }

  @Override
  public List<OrderVo> findOrderByUserIds(List<Long> userIds) {
    return null;
  }
}
