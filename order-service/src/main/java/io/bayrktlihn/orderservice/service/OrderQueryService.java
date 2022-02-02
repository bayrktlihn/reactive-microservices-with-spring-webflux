package io.bayrktlihn.orderservice.service;

import io.bayrktlihn.orderservice.dto.PurchaseOrderResponseDto;
import io.bayrktlihn.orderservice.entity.PurchaseOrder;
import io.bayrktlihn.orderservice.repository.PurchaseOrderRepository;
import io.bayrktlihn.orderservice.util.EntityDtoUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

  private final PurchaseOrderRepository purchaseOrderRepository;

  @Autowired
  public OrderQueryService(final PurchaseOrderRepository purchaseOrderRepository) {
    this.purchaseOrderRepository = purchaseOrderRepository;
  }

  public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
    final List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByUserId(userId);
    return Flux.fromStream(() -> purchaseOrderRepository.findByUserId(userId).stream()) // blocking
        .map(EntityDtoUtil::getPurchaseOrderResponseDto)
        .subscribeOn(Schedulers.boundedElastic());

  }
}
