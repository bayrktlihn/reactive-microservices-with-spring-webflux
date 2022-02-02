package io.bayrktlihn.webfluxdemo.config;

import java.util.function.BiFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CalculatorHandler {

  public Mono<ServerResponse> additionHandler(final ServerRequest request) {
    return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
  }

  public Mono<ServerResponse> subtractionHandler(final ServerRequest request) {
    return process(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
  }

  public Mono<ServerResponse> multiplicationHandler(final ServerRequest request) {
    return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
  }

  public Mono<ServerResponse> divisionHandler(final ServerRequest request) {
    return process(request, (a, b) -> {
      return b != 0 ? ServerResponse.ok().bodyValue(a / b) : ServerResponse.badRequest().bodyValue("b can not be 0");
    });
  }

  public Mono<ServerResponse> process(final ServerRequest request,
                                      final BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic) {
    final int a = getValue(request, "a");
    final int b = getValue(request, "b");
    return opLogic.apply(a, b);
  }


  private int getValue(final ServerRequest request, final String key) {
    return Integer.parseInt(request.pathVariable(key));
  }

}
