package io.bayrktlihn.webfluxdemo.webtestclient;

import io.bayrktlihn.webfluxdemo.controller.ReactiveMathController;
import io.bayrktlihn.webfluxdemo.dto.MultiplyRequestDto;
import io.bayrktlihn.webfluxdemo.dto.Response;
import io.bayrktlihn.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lec03ControllerPostTest {

  private final WebTestClient client;

  @MockBean
  private ReactiveMathService reactiveMathService;

  @Autowired
  public Lec03ControllerPostTest(final WebTestClient client) {
    this.client = client;
  }

  @Test
  public void postTest() {
    Mockito.when(reactiveMathService.multiply(Mockito.any())).thenReturn(Mono.just(Response.create(1)));

    client
        .post()
        .uri("/reactive-math/multiply")
        .accept(MediaType.APPLICATION_JSON)
        .headers(h -> h.setBasicAuth("username", "password"))
        .headers(h -> h.set("somekey", "somevalue"))
        .bodyValue(new MultiplyRequestDto())
        .exchange()
        .expectStatus().is2xxSuccessful();
  }
}
