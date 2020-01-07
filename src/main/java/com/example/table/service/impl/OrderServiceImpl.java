package com.example.table.service.impl;

import com.example.table.dao.OrderMapper;
import com.example.table.depots.DataPoint;
import com.example.table.depots.core.IRouting;
import com.example.table.entity.Order;
import com.example.table.request.OrderRequest;
import com.example.table.response.OrderVo;
import com.example.table.service.OrderService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
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

  private final IRouting routing;

  @Autowired
  public OrderServiceImpl(OrderMapper orderMapper, IRouting routing) {
    this.orderMapper = orderMapper;
    this.routing = routing;
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
  public void flushAll() {
    List<Order> fullTables = getFullTables();
    for (Order order : fullTables) {
      orderMapper.flushDb(order);
    }
  }

  /**
   * @Description planA
   * @Description 入参长度是n.需要执行n次sql查询
   */
  @Override
  public List<OrderVo> findOrderByOrderIdsV1(List<Long> orderIds) {
    List<Order> orders = new ArrayList<>();
    for (Long id : orderIds) {
      Order order = Order.builder().orderId(id).build();
      Order exist = orderMapper.findOrder(order);
      if (exist != null) {
        orders.add(exist);
      }
    }
    return Order.toVoList(orders);
  }


  /**
   * @Description planB
   * @Description m个库，每个库n张分区表，笛卡尔积是m*n，总分区表数量是m*n
   * @Description 每个分区扫描一次也可以得到全部结果集，扫描m*n次就可以得到最终结果
   */
  @Override
  public List<OrderVo> findOrderByOrderIdsV2(List<Long> orderIds) {
    List<Order> orders = new ArrayList<>();
    List<Order> fullTable = getFullTables();
    for (Order order : fullTable) {
      List<Order> exist = orderMapper.findOrderByOrderIdsV2(order, orderIds);
      if (CollectionUtils.isNotEmpty(exist)) {
        orders.addAll(exist);
      }
    }
    return Order.toVoList(orders);
  }

  private List<Order> getFullTables() {
    List<Order> tables = new ArrayList<>();
    // datasource00
    tables.add(Order.builder().orderId(1002L).build());
    tables.add(Order.builder().orderId(1008L).build());
    tables.add(Order.builder().orderId(1059L).build());
    tables.add(Order.builder().orderId(1005L).build());
    tables.add(Order.builder().orderId(1029L).build());
    // datasource01
    tables.add(Order.builder().orderId(1039L).build());
    tables.add(Order.builder().orderId(1003L).build());
    tables.add(Order.builder().orderId(1009L).build());
    tables.add(Order.builder().orderId(1069L).build());
    tables.add(Order.builder().orderId(1006L).build());
    // datasource02
    tables.add(Order.builder().orderId(1007L).build());
    tables.add(Order.builder().orderId(1049L).build());
    tables.add(Order.builder().orderId(1004L).build());
    tables.add(Order.builder().orderId(1019L).build());
    tables.add(Order.builder().orderId(1001L).build());
    return tables;

  }

  /**
   * @Description planC
   * @Description 入参长度是n, 对n个元素进行hash运算得到数据落点(实体库 + 实体表)
   * @Description 对实体库+实体表的组合分组,可以得到 1--m*n 种组合
   * @Description 最好的情况是只需要查询一次
   * @Description 最坏的情况是查询m*n
   */
  @Override
  public List<OrderVo> findOrderByOrderIdsV3(List<Long> orderIds) {
    Map<DataPoint, List<Long>> dataPointMap = getDataPointMap(orderIds);
    List<Order> orders = new ArrayList<>();
    for (DataPoint point : dataPointMap.keySet()) {
      List<Long> ids = dataPointMap.get(point);
      Order order = Order.builder().orderId(ids.get(0)).build();
      List<Order> exist = orderMapper.findOrderByOrderIdsV2(order, ids);
      if (CollectionUtils.isNotEmpty(exist)) {
        orders.addAll(exist);
      }
    }
    return Order.toVoList(orders);
  }


  private Map<DataPoint, List<Long>> getDataPointMap(List<Long> orderIds) {
    Map<DataPoint, List<Long>> map = new HashMap<>(orderIds.size());
    for (long id : orderIds) {
      DataPoint point = getDataPointByRoutingField(id);
      if (!map.containsKey(point)) {
        List<Long> list = new ArrayList<>();
        list.add(id);
        map.put(point, list);
      } else {
        List<Long> exist = map.get(point);
        exist.add(id);
        map.put(point, exist);
      }
    }
    return map;
  }


  private DataPoint getDataPointByRoutingField(long routingFieldValue) {
    String fieldValue = String.valueOf(routingFieldValue);
    String datasource = routing.calDataSourceKey(fieldValue);
    String tableKey = routing.calTableKey(fieldValue);
    return DataPoint.builder().dataSource(datasource).tableSuffix(tableKey).build();

  }


  /**
   * @Description planA
   * @Description 扫描所有的分区表
   * @Description 查询次数m*n
   */
  @Override
  public List<OrderVo> findOrderByUserIdV1(Long userId) {
    List<Order> orders = new ArrayList<>();
    List<Order> fullTables = getFullTables();
    for (Order order : fullTables) {
      List<Order> exist = orderMapper.findOrderByUserIdV1(order, userId);
      if (CollectionUtils.isNotEmpty(exist)) {
        orders.addAll(exist);
      }
    }
    return Order.toVoList(orders);
  }

  /**
   * @Description planB
   * @Description userId不是路由字段，怎么击中数据呢？
   * @Description ES OR Redis 存贮 userId 与 orderId的映射关系
   * @Description 获取orderId结果集 然后根据orderId 路由击中数据
   * @Description 但是 userId -> orderIds 数据同步Redis Or ES 实时性、一致性如何保证(CAP)
   */
  @Override
  public List<OrderVo> findOrderByUserIdV2(Long userId) {
    return null;
  }

  /**
   * @param userIds
   * @return
   * @Description 查询每个userIdd的订单，金额倒序排列
   */
  @Override
  public List<OrderVo> findOrderByUserIdsAndPage(List<Long> userIds, int pageNum, int pageSize) {
    return null;
  }


}
