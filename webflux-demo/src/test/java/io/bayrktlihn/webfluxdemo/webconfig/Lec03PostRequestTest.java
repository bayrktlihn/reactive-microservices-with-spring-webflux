package io.bayrktlihn.webfluxdemo.webconfig;


import io.bayrktlihn.webfluxdemo.dto.MultiplyRequestDto;
import io.bayrktlihn.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec03PostRequestTest extends BaseTest {

  @Autowired
  private WebClient webClient;

  @Test
  public void postTest() {
    final Mono<Response> responseMono = webClient
        .post()
        .uri("reactive-math/multiply")
        .bodyValue(buildRequestDto(5, 2))
        .retrieve()
        .bodyToMono(Response.class)
        .doOnNext(System.out::println);

    StepVerifier.create(responseMono)
        .expectNextCount(1)
        .verifyComplete();
  }

  private MultiplyRequestDto buildRequestDto(final int a, final int b) {
    final MultiplyRequestDto dto = new MultiplyRequestDto();
    dto.setFirst(a);
    dto.setSecond(b);
    return dto;
  }
}
