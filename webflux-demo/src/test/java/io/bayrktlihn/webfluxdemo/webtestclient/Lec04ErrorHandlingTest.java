package io.bayrktlihn.webfluxdemo.webtestclient;


import io.bayrktlihn.webfluxdemo.controller.ReactiveMathValidationController;
import io.bayrktlihn.webfluxdemo.dto.Response;
import io.bayrktlihn.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathValidationController.class)
public class Lec04ErrorHandlingTest {

  private final WebTestClient client;

  @MockBean
  private ReactiveMathService reactiveMathService;

  @Autowired
  public Lec04ErrorHandlingTest(final WebTestClient client) {
    this.client = client;
  }

  @Test
  public void errorHandlingTest() {
    Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(Response.create(1)));

    client
        .get()
        .uri("/reactive-math/square/{number}/throw", 5)
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody()
        .jsonPath("$.message").isEqualTo("allowed range is 10 - 20")
        .jsonPath("$.errorCode").isEqualTo(100)
        .jsonPath("$.input").isEqualTo(5);
  }

}
