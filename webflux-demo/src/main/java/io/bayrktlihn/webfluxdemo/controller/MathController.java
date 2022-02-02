package io.bayrktlihn.webfluxdemo.controller;

import io.bayrktlihn.webfluxdemo.dto.Response;
import io.bayrktlihn.webfluxdemo.service.MathService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("math")
public class MathController {

  private final MathService mathService;

  @Autowired
  public MathController(final MathService mathService) {
    this.mathService = mathService;
  }

  @GetMapping("square/{input}")
  public Response findSquare(@PathVariable int input) {
    return mathService.findSquare(input);
  }

  @GetMapping("table/{input}")
  public List<Response> multiplicationTable(@PathVariable int input) {
    return mathService.multiplicationTable(input);
  }
}
