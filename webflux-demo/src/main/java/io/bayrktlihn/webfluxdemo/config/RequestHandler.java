package io.bayrktlihn.webfluxdemo.config;

import io.bayrktlihn.webfluxdemo.dto.MultiplyRequestDto;
import io.bayrktlihn.webfluxdemo.dto.Response;
import io.bayrktlihn.webfluxdemo.exception.InputValidationException;
import io.bayrktlihn.webfluxdemo.service.ReactiveMathService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

  private final ReactiveMathService mathService;

  @Autowired
  private RequestHandler(final ReactiveMathService mathService) {
    this.mathService = mathService;
  }

  public Mono<ServerResponse> squareHandler(final ServerRequest serverRequest) {
    final int input = Integer.parseInt(serverRequest.pathVariable("input"));
    final Mono<Response> responseMono = mathService.findSquare(input);
    return ServerResponse.ok().body(responseMono, Response.class);
  }

  public Mono<ServerResponse> tableHandler(final ServerRequest serverRequest) {
    final int input = Integer.parseInt(serverRequest.pathVariable("input"));
    final Flux<Response> responseMono = mathService.multiplicationTable(input);
    return ServerResponse.ok().body(responseMono, Response.class);
  }

  public Mono<ServerResponse> tableStreamHandler(final ServerRequest serverRequest) {
    final int input = Integer.parseInt(serverRequest.pathVariable("input"));
    final Flux<Response> responseMono = mathService.multiplicationTable(input);
    return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(responseMono, Response.class);
  }

  public Mono<ServerResponse> multiplyHandler(final ServerRequest serverRequest) {
    final Mono<MultiplyRequestDto> requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
    final Mono<Response> responseMono = mathService.multiply(requestDtoMono);
    return ServerResponse.ok().body(responseMono, Response.class);
  }

  public Mono<ServerResponse> squareHandlerWithValidation(final ServerRequest serverRequest) {
    final int input = Integer.parseInt(serverRequest.pathVariable("input"));

    if (input < 10 || input > 20) {
      return Mono.error(new InputValidationException(input));
    }

    final Mono<Response> responseMono = mathService.findSquare(input);
    return ServerResponse.ok().body(responseMono, Response.class);
  }

  public Mono<ServerResponse> calculateHandler(final ServerRequest serverRequest) {
    final int first = Integer.parseInt(serverRequest.pathVariable("first"));
    final int second = Integer.parseInt(serverRequest.pathVariable("second"));
    final String operationName = serverRequest.headers().firstHeader("operationName");

    switch (Objects.requireNonNull(operationName)) {
      case "+": {
        final Mono<Response> mono = Mono.just(Response.create(first + second));
        return ServerResponse.ok().body(mono, Response.class);
      }
      case "-": {
        final Mono<Response> mono = Mono.just(Response.create(first - second));
        return ServerResponse.ok().body(mono, Response.class);
      }
      case "*": {
        final Mono<Response> mono = Mono.just(Response.create(first * second));
        return ServerResponse.ok().body(mono, Response.class);
      }
      case "/": {
        final Mono<Response> mono = Mono.just(Response.create(first / second));
        return ServerResponse.ok().body(mono, Response.class);
      }
      default:
        return ServerResponse.ok().body(Mono.error(new RuntimeException()), Response.class);
    }
  }
}
