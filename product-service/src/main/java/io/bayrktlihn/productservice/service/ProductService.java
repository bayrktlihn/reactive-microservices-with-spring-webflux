package io.bayrktlihn.productservice.service;

import io.bayrktlihn.productservice.dto.ProductDto;
import io.bayrktlihn.productservice.repository.ProductRepository;
import io.bayrktlihn.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks.Many;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final Many<ProductDto> sink;

  @Autowired
  public ProductService(final ProductRepository productRepository,
                        final Many<ProductDto> sink) {
    this.productRepository = productRepository;
    this.sink = sink;
  }


  public Flux<ProductDto> getAll() {
    return productRepository.findAll()
        .map(EntityDtoUtil::toDto);
  }

  public Flux<ProductDto> getProductByPriceRange(final int min, final int max) {
    return productRepository.findByPriceBetween(Range.closed(min, max))
        .map(EntityDtoUtil::toDto);
  }

  public Mono<ProductDto> getProductById(final String id) {
    return productRepository.findById(id)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<ProductDto> insertProduct(final Mono<ProductDto> productDtoMono) {
    return productDtoMono
        .map(EntityDtoUtil::toEntity)
        .flatMap(productRepository::insert)
        .map(EntityDtoUtil::toDto)
        .doOnNext(sink::tryEmitNext);

  }

  public Mono<ProductDto> updateProduct(final String id, final Mono<ProductDto> productDtoMono) {
    return productRepository.findById(id)
        .flatMap(p -> productDtoMono.map(EntityDtoUtil::toEntity).doOnNext(e -> e.setId(id)))
        .flatMap(productRepository::save)
        .map(EntityDtoUtil::toDto);

  }

  public Mono<Void> deleteProduct(final String id) {
    return productRepository.deleteById(id);
  }

}
