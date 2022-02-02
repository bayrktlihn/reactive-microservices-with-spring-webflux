package io.bayrktlihn.webfluxdemo.webtestclient;

import io.bayrktlihn.webfluxdemo.controller.ParamsController;
import io.bayrktlihn.webfluxdemo.controller.ReactiveMathController;
import io.bayrktlihn.webfluxdemo.dto.Response;
import io.bayrktlihn.webfluxdemo.service.ReactiveMathService;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class Lec02ControllerGetTest {

  private final WebTestClient client;

  @MockBean
  private ReactiveMathService reactiveMathService;

  @Autowired
  public Lec02ControllerGetTest(final WebTestClient client) {
    this.client = client;
  }

  @Test
  public void singleResponseTest() {

    Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.empty());

    client
        .get()
        .uri("/reactive-math/square/{number}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody(Response.class)
        .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(-1));
  }


  @Test
  public void listResponseTest() {

    final Flux<Response> flux = Flux.range(1, 3)
        .map(Response::create);

    Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

    client
        .get()
        .uri("/reactive-math/table/{number}", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Response.class)
        .hasSize(3);
  }

  @Test
  public void streamingResponseTest() {

    final Flux<Response> flux = Flux.range(1, 3)
        .map(Response::create)
        .delayElements(Duration.ofMillis(100));

    Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

    client
        .get()
        .uri("/reactive-math/table/{number}/stream", 5)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
        .expectBodyList(Response.class)
        .hasSize(3);
  }

  @Test
  public void paramTest() {
    final Map<String, Integer> map = new HashMap<>();
    map.put("count", 10);
    map.put("page", 20);

    client
        .get()
        .uri(b -> b.path("/jobs/search").query("count={count}&page={page}").build(map))
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBodyList(Integer.class)
        .hasSize(2).contains(10, 20);
  }
}
