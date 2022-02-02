package io.bayrktlihn.orderservice.util;

import io.bayrktlihn.orderservice.dto.OrderStatus;
import io.bayrktlihn.orderservice.dto.PurchaseOrderResponseDto;
import io.bayrktlihn.orderservice.dto.RequestContext;
import io.bayrktlihn.orderservice.dto.TransactionRequestDto;
import io.bayrktlihn.orderservice.dto.TransactionStatus;
import io.bayrktlihn.orderservice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

  public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(final PurchaseOrder purchaseOrder) {
    final PurchaseOrderResponseDto purchaseOrderResponseDto = new PurchaseOrderResponseDto();
    BeanUtils.copyProperties(purchaseOrder, purchaseOrderResponseDto);
    purchaseOrderResponseDto.setOrderId(purchaseOrder.getId());
    return purchaseOrderResponseDto;
  }

  public static void setTransactionRequestDto(final RequestContext requestContext) {
    final TransactionRequestDto dto = new TransactionRequestDto();
    dto.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
    dto.setAmount(requestContext.getProductDto().getPrice());
    requestContext.setTransactionRequestDto(dto);
  }

  public static PurchaseOrder getPurchaseOrder(final RequestContext requestContext) {
    final PurchaseOrder purchaseOrder = new PurchaseOrder();
    purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDto().getUserId());
    purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDto().getProductId());
    purchaseOrder.setAmount(requestContext.getProductDto().getPrice());

    final TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
    final OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
    purchaseOrder.setStatus(orderStatus);
    return purchaseOrder;
  }

}
