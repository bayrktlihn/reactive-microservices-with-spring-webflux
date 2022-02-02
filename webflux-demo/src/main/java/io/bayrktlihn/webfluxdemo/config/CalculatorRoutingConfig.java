package io.bayrktlihn.webfluxdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculatorRoutingConfig {

  private final CalculatorHandler handler;

  @Autowired
  public CalculatorRoutingConfig(final CalculatorHandler handler) {
    this.handler = handler;
  }

  @Bean
  public RouterFunction<ServerResponse> highLevelCalculatorRouter() {
    return RouterFunctions.route()
        .path("calculator", this::serverResponseRouterFunction)
        .build();
  }

  //  @Bean
  public RouterFunction<ServerResponse> serverResponseRouterFunction() {
    return RouterFunctions.route()
        .GET("{a}/{b}", isOperation("+"), handler::additionHandler)
        .GET("{a}/{b}", isOperation("-"), handler::subtractionHandler)
        .GET("{a}/{b}", isOperation("*"), handler::multiplicationHandler)
        .GET("{a}/{b}", isOperation("/"), handler::divisionHandler)
        .GET("{a}/{b}", request -> ServerResponse.badRequest().bodyValue("OP should be + - * 7"))
        .build();
  }

  private RequestPredicate isOperation(final String operation) {
    return RequestPredicates.headers(headers -> operation.equals(headers.asHttpHeaders().toSingleValueMap().get("OP")));
  }


}
