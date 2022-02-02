package io.bayrktlihn.productservice.util;

import io.bayrktlihn.productservice.dto.ProductDto;
import io.bayrktlihn.productservice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

  public static ProductDto toDto(final Product product) {
    final ProductDto dto = new ProductDto();
    BeanUtils.copyProperties(product, dto);
    return dto;
  }

  public static Product toEntity(final ProductDto productDto) {
    final Product product = new Product();
    BeanUtils.copyProperties(productDto, product);
    return product;
  }
}
