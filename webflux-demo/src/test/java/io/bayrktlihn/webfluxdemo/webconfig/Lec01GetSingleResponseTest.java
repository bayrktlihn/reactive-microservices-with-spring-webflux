package io.bayrktlihn.webfluxdemo.webconfig;

import io.bayrktlihn.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {

  @Autowired
  private WebClient webClient;

  @Test
  public void blockTest() {
    final Response response = webClient
        .get()
        .uri("reactive-math/square/{number}", 5)
        .retrieve()
        .bodyToMono(Response.class)
        .block();

    System.out.println(response);

  }

  @Test
  public void stepVerifierTest() {
    final Mono<Response> responseMono = webClient
        .get()
        .uri("reactive-math/square/{number}", 5)
        .retrieve()
        .bodyToMono(Response.class);

    StepVerifier.create(responseMono)
        .expectNextMatches(r -> r.getOutput() == 25)
        .verifyComplete();
  }
}
