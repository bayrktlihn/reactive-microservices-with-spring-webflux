package io.bayrktlihn.webfluxdemo.webtestclient;

import io.bayrktlihn.webfluxdemo.config.RequestHandler;
import io.bayrktlihn.webfluxdemo.config.RouterConfig;
import io.bayrktlihn.webfluxdemo.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctionTest {

  private WebTestClient client;

  private final ApplicationContext ctxt;

  @MockBean
  private RequestHandler handler;

  @Autowired
  public Lec05RouterFunctionTest(final ApplicationContext ctxt) {
    this.ctxt = ctxt;
  }

  @BeforeAll
  public void setClient() {
    client = WebTestClient.bindToApplicationContext(ctxt).build();
  }


  @Test
  public void test() {
    Mockito.when(handler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(Response.create(225)));

    client
        .get()
        .uri("/router/square/{input}", 15)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(Response.class)
        .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
  }

}
