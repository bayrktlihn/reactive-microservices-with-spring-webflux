package io.bayrktlihn.orderservice.controller;

import io.bayrktlihn.orderservice.dto.PurchaseOrderRequestDto;
import io.bayrktlihn.orderservice.dto.PurchaseOrderResponseDto;
import io.bayrktlihn.orderservice.service.OrderFulfillmentService;
import io.bayrktlihn.orderservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

  private final OrderFulfillmentService orderFulfillmentService;
  private final OrderQueryService orderQueryService;

  @Autowired
  public PurchaseOrderController(final OrderFulfillmentService orderFulfillmentService,
                                 final OrderQueryService orderQueryService) {
    this.orderFulfillmentService = orderFulfillmentService;
    this.orderQueryService = orderQueryService;
  }

  @PostMapping
  public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody final Mono<PurchaseOrderRequestDto> requestDtoMono) {
    return orderFulfillmentService.processOrder(requestDtoMono)
        .map(ResponseEntity::ok)
        .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
        .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
  }

  @GetMapping("user/{id}")
  public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable final int userId) {
    return orderQueryService.getProductsByUserId(userId);
  }
}
