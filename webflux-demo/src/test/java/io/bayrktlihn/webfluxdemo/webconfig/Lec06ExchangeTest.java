package io.bayrktlihn.webfluxdemo.webconfig;


import io.bayrktlihn.webfluxdemo.dto.InputFailedValidationResponse;
import io.bayrktlihn.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

  @Autowired
  private WebClient webClient;

  //exchange = retrieve + additional info http status code

  @Test
  public void badRequestTest() {
    final Mono<Object> responseFlux = webClient
        .get()
        .uri("reactive-math/square/{number}/throw", 5)
        .exchangeToMono(this::exchange)
        .doOnNext(System.out::println)
        .doOnError(err -> System.out.println(err.getMessage()));

    StepVerifier.create(responseFlux)
        .expectNextCount(1)
        .verifyError(WebClientResponseException.BadRequest.class);

  }

  private Mono<Object> exchange(final ClientResponse cr) {
    if (cr.rawStatusCode() == 400) {
      return cr.bodyToMono(InputFailedValidationResponse.class);
    } else {
      return cr.bodyToMono(Response.class);
    }
  }


}
