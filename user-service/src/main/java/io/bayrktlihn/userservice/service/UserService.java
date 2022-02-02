package io.bayrktlihn.userservice.service;

import io.bayrktlihn.userservice.dto.UserDto;
import io.bayrktlihn.userservice.repository.UserRepository;
import io.bayrktlihn.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Flux<UserDto> all() {
    return userRepository.findAll()
        .map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> getUserById(final int userId) {
    return userRepository.findById(userId)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> createUser(final Mono<UserDto> userDtoMono) {
    return userDtoMono
        .map(EntityDtoUtil::toEntity)
        .flatMap(userRepository::save)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> updateUser(final int userId, final Mono<UserDto> userDtoMono) {
    return userRepository.findById(userId)
        .flatMap(user ->
                     userDtoMono.map(EntityDtoUtil::toEntity)
                         .doOnNext(e -> e.setId(userId)))
        .flatMap(userRepository::save)
        .map(EntityDtoUtil::toDto);

  }

  public Mono<Void> deleteUser(final int userId) {
    return userRepository.deleteById(userId);
  }
}
