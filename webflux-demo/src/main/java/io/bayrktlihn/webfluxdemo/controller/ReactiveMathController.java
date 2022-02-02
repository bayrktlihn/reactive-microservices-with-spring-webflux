package io.bayrktlihn.webfluxdemo.controller;

import io.bayrktlihn.webfluxdemo.dto.MultiplyRequestDto;
import io.bayrktlihn.webfluxdemo.dto.Response;
import io.bayrktlihn.webfluxdemo.service.ReactiveMathService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

  private final ReactiveMathService mathService;

  @Autowired
  private ReactiveMathController(final ReactiveMathService mathService) {
    this.mathService = mathService;
  }


  @GetMapping("square/{input}")
  public Mono<Response> findSquare(@PathVariable int input) {
    return mathService.findSquare(input)
        .defaultIfEmpty(Response.create(-1));
  }

  @GetMapping("table/{input}")
  public Flux<Response> multiplicationTable(@PathVariable int input) {
    return mathService.multiplicationTable(input);
  }

  @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Response> multiplicationTableStream(@PathVariable int input) {
    return mathService.multiplicationTable(input);
  }

  @PostMapping("multiply")
  public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono,
                                 @RequestHeader Map<String, String> headers) {
    System.out.println(headers);
    return mathService.multiply(requestDtoMono);
  }
}
