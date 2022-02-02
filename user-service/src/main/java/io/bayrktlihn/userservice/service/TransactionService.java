package io.bayrktlihn.userservice.service;

import io.bayrktlihn.userservice.dto.TransactionRequestDto;
import io.bayrktlihn.userservice.dto.TransactionResponseDto;
import io.bayrktlihn.userservice.dto.TransactionStatus;
import io.bayrktlihn.userservice.entity.UserTransaction;
import io.bayrktlihn.userservice.repository.UserRepository;
import io.bayrktlihn.userservice.repository.UserTransactionRepository;
import io.bayrktlihn.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

  private final UserRepository userRepository;
  private final UserTransactionRepository userTransactionRepository;

  @Autowired
  public TransactionService(final UserRepository userRepository, final UserTransactionRepository userTransactionRepository) {
    this.userRepository = userRepository;
    this.userTransactionRepository = userTransactionRepository;
  }

  public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
    return userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
        .filter(Boolean::booleanValue)
        .map(b -> EntityDtoUtil.toEntity(requestDto))
        .flatMap(userTransactionRepository::save)
        .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
        .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
  }

  public Flux<UserTransaction> getByUserId(final int userId) {
    return userTransactionRepository.findByUserId(userId);
  }
}
