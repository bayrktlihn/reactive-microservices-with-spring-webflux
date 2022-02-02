package io.bayrktlihn.orderservice.entity;

import io.bayrktlihn.orderservice.dto.OrderStatus;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class PurchaseOrder {

  @Id
  @GeneratedValue
  private Integer id;
  private String productId;
  private Integer userId;
  private Integer amount;
  private OrderStatus status;

}
