package io.bayrktlihn.productservice.controller;


import io.bayrktlihn.productservice.dto.ProductDto;
import io.bayrktlihn.productservice.service.ProductService;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(final ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("all")
  public Flux<ProductDto> all() {
    return productService.getAll();
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable final String id) {
    simulateRandomException();
    return productService.getProductById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("price-range")
  public Flux<ProductDto> getByPriceRange(@RequestParam("min") final int min, @RequestParam("max") final int max) {
    return productService.getProductByPriceRange(min, max);
  }

  @PostMapping
  public Mono<ProductDto> insertProduct(@RequestBody final Mono<ProductDto> productDtoMono) {
    return productService.insertProduct(productDtoMono);
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable final String id, @RequestBody Mono<ProductDto> productDtoMono) {
    return productService.updateProduct(id, productDtoMono)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public Mono<Void> deleteProduct(@PathVariable("id") final String id) {
    return productService.deleteProduct(id);
  }

  private void simulateRandomException() {
    final int nextInt = ThreadLocalRandom.current().nextInt(1, 10);

    if (nextInt > 5) {
      throw new RuntimeException("something is wrong");
    }
  }
}
