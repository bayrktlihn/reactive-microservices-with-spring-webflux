package io.bayrktlihn.orderservice.client;

import io.bayrktlihn.orderservice.dto.TransactionRequestDto;
import io.bayrktlihn.orderservice.dto.TransactionResponseDto;
import io.bayrktlihn.orderservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

  private final WebClient webClient;

  public UserClient(@Value("${user.service.url}") final String url) {
    webClient = WebClient.builder()
        .baseUrl(url)
        .build();
  }

  public Mono<TransactionResponseDto> authorizeTransaction(final TransactionRequestDto requestDto) {
    return webClient
        .post()
        .uri("transaction")
        .bodyValue(requestDto)
        .retrieve()
        .bodyToMono(TransactionResponseDto.class);

  }

  public Flux<UserDto> getAllUsers() {
    return webClient.get()
        .uri("all")
        .retrieve()
        .bodyToFlux(UserDto.class);
  }
}
