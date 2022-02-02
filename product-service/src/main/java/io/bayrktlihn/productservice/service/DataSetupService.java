package io.bayrktlihn.productservice.service;

import io.bayrktlihn.productservice.dto.ProductDto;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

  private final ProductService productService;

  @Autowired
  public DataSetupService(final ProductService productService) {
    this.productService = productService;
  }

  @Override
  public void run(final String... args) throws Exception {
    final ProductDto p1 = ProductDto.create("4k-tv", 1000);
    final ProductDto p2 = ProductDto.create("slr-camera", 750);
    final ProductDto p3 = ProductDto.create("iphone", 800);
    final ProductDto p4 = ProductDto.create("headphone", 100);

    Flux.just(p1, p2, p3, p4)
        .concatWith(newProducts())
        .flatMap(p -> productService.insertProduct(Mono.just(p)))
        .subscribe(System.out::println);

  }

  private Flux<ProductDto> newProducts() {
    return Flux.range(1, 1000)
        .delayElements(Duration.ofSeconds(2))
        .map(i -> new ProductDto("product-" + i, ThreadLocalRandom.current().nextInt(10, 100)));
  }
}
