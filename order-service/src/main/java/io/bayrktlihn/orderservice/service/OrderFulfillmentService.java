package io.bayrktlihn.orderservice.service;

import io.bayrktlihn.orderservice.client.ProductClient;
import io.bayrktlihn.orderservice.client.UserClient;
import io.bayrktlihn.orderservice.dto.PurchaseOrderRequestDto;
import io.bayrktlihn.orderservice.dto.PurchaseOrderResponseDto;
import io.bayrktlihn.orderservice.dto.RequestContext;
import io.bayrktlihn.orderservice.repository.PurchaseOrderRepository;
import io.bayrktlihn.orderservice.util.EntityDtoUtil;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
public class OrderFulfillmentService {

  private final ProductClient productClient;
  private final UserClient userClient;
  private final PurchaseOrderRepository purchaseOrderRepository;

  @Autowired
  public OrderFulfillmentService(final ProductClient productClient,
                                 final UserClient userClient,
                                 final PurchaseOrderRepository purchaseOrderRepository) {
    this.productClient = productClient;
    this.userClient = userClient;
    this.purchaseOrderRepository = purchaseOrderRepository;
  }

  public Mono<PurchaseOrderResponseDto> processOrder(final Mono<PurchaseOrderRequestDto> requestDtoMono) {
    return requestDtoMono.map(RequestContext::new)
        .flatMap(this::productRequestResponse)
        .doOnNext(EntityDtoUtil::setTransactionRequestDto)
        .flatMap(this::userRequestResponse)
        .map(EntityDtoUtil::getPurchaseOrder)
        .map(purchaseOrderRepository::save) // blocking
        .map(EntityDtoUtil::getPurchaseOrderResponseDto)
        .subscribeOn(Schedulers.boundedElastic());
  }

  private Mono<RequestContext> productRequestResponse(final RequestContext rc) {
    return productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
        .doOnNext(rc::setProductDto)
        .thenReturn(rc);
  }

  private Mono<RequestContext> userRequestResponse(final RequestContext rc) {
    return userClient.authorizeTransaction(rc.getTransactionRequestDto())
        .doOnNext(rc::setTransactionResponseDto)
        .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
        .thenReturn(rc);
  }
}
