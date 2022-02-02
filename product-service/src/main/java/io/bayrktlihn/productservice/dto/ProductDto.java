package io.bayrktlihn.productservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductDto {

  private String id;
  private String description;
  private Integer price;

  public ProductDto(final String description, final Integer price) {
    this.description = description;
    this.price = price;
  }

  public static ProductDto create(final String description, final Integer price) {
    return new ProductDto(description, price);
  }
}
