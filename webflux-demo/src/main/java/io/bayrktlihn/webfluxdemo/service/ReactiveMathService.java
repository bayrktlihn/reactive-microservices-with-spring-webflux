package io.bayrktlihn.webfluxdemo.service;

import io.bayrktlihn.webfluxdemo.dto.MultiplyRequestDto;
import io.bayrktlihn.webfluxdemo.dto.Response;
import java.time.Duration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

  public Mono<Response> findSquare(final int input) {
    return Mono.fromSupplier(() -> input * input).map(Response::create);
  }

  public Flux<Response> multiplicationTable(final int input) {
    return Flux.range(1, 10)
        .delayElements(Duration.ofSeconds(1))
//        .doOnNext(i -> SleepUtil.sleepSeconds(1))
        .doOnNext(i -> System.out.println("reactive-math-service processing : " + i))
        .map(i -> Response.create(i * input));

  }

  public Mono<Response> multiply(final Mono<MultiplyRequestDto> dtoMono) {
    return dtoMono.map(dto -> dto.getFirst() * dto.getSecond())
        .map(Response::create);
  }

}
