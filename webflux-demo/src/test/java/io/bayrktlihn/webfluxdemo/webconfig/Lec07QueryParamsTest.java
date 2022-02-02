package io.bayrktlihn.webfluxdemo.webconfig;


import io.bayrktlihn.webfluxdemo.dto.InputFailedValidationResponse;
import io.bayrktlihn.webfluxdemo.dto.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec07QueryParamsTest extends BaseTest {

  @Autowired
  private WebClient webClient;

  final String queryString = "http://localhost:8080/jobs/search?count={count}&page={name}";

  @Test
  public void queryParamsTest() {

//    final URI uri = UriComponentsBuilder.fromUriString(queryString).build(10, 20);

    final Map<String, Integer> map = new HashMap<>();
    map.put("count", 10);
    map.put("page", 20);

    final Flux<Integer> responseFlux = webClient
        .get()
        .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(map))
        .retrieve()
        .bodyToFlux(Integer.class)
        .doOnNext(System.out::println);

    StepVerifier.create(responseFlux)
        .expectNextCount(2)
        .verifyComplete();

  }

  private Mono<Object> exchange(final ClientResponse cr) {
    if (cr.rawStatusCode() == 400) {
      return cr.bodyToMono(InputFailedValidationResponse.class);
    } else {
      return cr.bodyToMono(Response.class);
    }
  }


}
