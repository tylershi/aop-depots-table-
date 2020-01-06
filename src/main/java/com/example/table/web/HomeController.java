package com.example.table.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:13
 * @Version 1.0
 * @Description
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {

  @GetMapping(value = {"/", "api"})
  public String swaggerPage() {
    return "redirect:/swagger-ui.html";
  }

}
