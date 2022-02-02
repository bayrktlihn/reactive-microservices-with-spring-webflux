package io.bayrktlihn.orderservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PurchaseOrderResponseDto {

  private Integer orderId;
  private Integer userId;
  private String productId;
  private Integer amount;
  private OrderStatus status;

}
