package io.bayrktlihn.userservice.controller;

import io.bayrktlihn.userservice.dto.TransactionRequestDto;
import io.bayrktlihn.userservice.dto.TransactionResponseDto;
import io.bayrktlihn.userservice.entity.UserTransaction;
import io.bayrktlihn.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

  private final TransactionService transactionService;

  @Autowired
  private UserTransactionController(final TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping
  public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
    return requestDtoMono.flatMap(transactionService::createTransaction);
  }

  @GetMapping
  public Flux<UserTransaction> getByUserId(@RequestParam int userId) {
    return transactionService.getByUserId(userId);
  }
}
