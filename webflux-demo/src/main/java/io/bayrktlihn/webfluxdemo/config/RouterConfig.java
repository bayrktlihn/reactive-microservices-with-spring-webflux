package io.bayrktlihn.webfluxdemo.config;


import io.bayrktlihn.webfluxdemo.dto.InputFailedValidationResponse;
import io.bayrktlihn.webfluxdemo.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {

  private final RequestHandler handler;

  @Autowired
  public RouterConfig(final RequestHandler handler) {
    this.handler = handler;
  }

  @Bean
  public RouterFunction<ServerResponse> highLevelRouter() {
    return RouterFunctions.route()
        .path("router", this::serverResponseRouterFunction)
        .build();
  }

  //  @Bean
  public RouterFunction<ServerResponse> serverResponseRouterFunction() {
    return RouterFunctions.route()
        .GET("square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), handler::squareHandler)
        .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
        .GET("table/{input}", handler::tableHandler)
        .GET("tableStream/{input}", handler::tableStreamHandler)
        .POST("multiply", handler::multiplyHandler)
        .GET("square/{input}/validation", handler::squareHandlerWithValidation)
        .GET("calculator/{first}/{second}", handler::calculateHandler)
        .onError(InputValidationException.class, this::exceptionHandler)
        .build();
  }

  private Mono<ServerResponse> exceptionHandler(final Throwable throwable, final ServerRequest serverRequest) {
    final InputValidationException ex = (InputValidationException) throwable;
    final InputFailedValidationResponse inputFailedValidationResponse = new InputFailedValidationResponse();
    inputFailedValidationResponse.setInput(ex.getInput());
    inputFailedValidationResponse.setMessage(ex.getMessage());
    inputFailedValidationResponse.setErrorCode(ex.getErrorCode());
    return ServerResponse.badRequest().bodyValue(inputFailedValidationResponse);
  }

}
