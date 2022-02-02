package io.bayrktlihn.webfluxdemo.webtestclient;

import io.bayrktlihn.webfluxdemo.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class Lec01SimpleWebTestClientTest {

  private final WebTestClient client;

  @Autowired
  public Lec01SimpleWebTestClientTest(final WebTestClient client) {
    this.client = client;
  }

  @Test
  public void stepVerifierTest() {
    final Flux<Response> responseMono = client
        .get()
        .uri("/reactive-math/square/{number}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .returnResult(Response.class)
        .getResponseBody();

    StepVerifier.create(responseMono)
        .expectNextMatches(r -> r.getOutput() == 25)
        .verifyComplete();

  }


  @Test
  public void fluentAssertionTest() {
    client
        .get()
        .uri("/reactive-math/square/{number}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody(Response.class)
        .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(25));
  }
}
