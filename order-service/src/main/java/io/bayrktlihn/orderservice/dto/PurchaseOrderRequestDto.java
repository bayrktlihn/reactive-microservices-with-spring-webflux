package io.bayrktlihn.orderservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PurchaseOrderRequestDto {

  private Integer userId;
  private String productId;

}
