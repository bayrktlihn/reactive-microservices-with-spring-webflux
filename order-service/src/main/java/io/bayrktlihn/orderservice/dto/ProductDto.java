package io.bayrktlihn.orderservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDto {

  private String id;
  private String description;
  private Integer price;

  public ProductDto(final String description, final Integer price) {
    this.description = description;
    this.price = price;
  }
}
