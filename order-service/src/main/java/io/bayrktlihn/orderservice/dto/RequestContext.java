package io.bayrktlihn.orderservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RequestContext {

  private PurchaseOrderRequestDto purchaseOrderRequestDto;
  private ProductDto productDto;
  private TransactionRequestDto transactionRequestDto;
  private TransactionResponseDto transactionResponseDto;

  public RequestContext(final PurchaseOrderRequestDto purchaseOrderRequestDto) {
    this.purchaseOrderRequestDto = purchaseOrderRequestDto;
  }
}
