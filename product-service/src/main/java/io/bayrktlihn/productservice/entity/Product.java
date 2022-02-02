package io.bayrktlihn.productservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product {

  @Id
  private String id;
  private String description;
  private Integer price;


  private Product(final String description, final Integer price) {
    this.description = description;
    this.price = price;
  }

  public static Product create(final String description, final Integer price) {
    return new Product(description, price);
  }
}
