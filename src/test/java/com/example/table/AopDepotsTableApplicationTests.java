package com.example.table;

import com.example.table.request.OrderRequest;
import com.example.table.service.OrderService;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopDepotsTableApplicationTests {

  @Resource
  private OrderService orderService;

  /*@Test
  public void testInitData() {
    for (long i = 1000; i < 2000; i++) {
      OrderRequest order = OrderRequest.builder().orderId(i).userId(i).money(i * 1.0).build();
      orderService.saveOrder(order);
    }
  }*/

}
