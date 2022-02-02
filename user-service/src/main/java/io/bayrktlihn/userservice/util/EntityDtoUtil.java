package io.bayrktlihn.userservice.util;

import io.bayrktlihn.userservice.dto.TransactionRequestDto;
import io.bayrktlihn.userservice.dto.TransactionResponseDto;
import io.bayrktlihn.userservice.dto.TransactionStatus;
import io.bayrktlihn.userservice.dto.UserDto;
import io.bayrktlihn.userservice.entity.User;
import io.bayrktlihn.userservice.entity.UserTransaction;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

  public static UserDto toDto(final User user) {
    final UserDto dto = new UserDto();
    BeanUtils.copyProperties(user, dto);
    return dto;
  }

  public static User toEntity(final UserDto dto) {
    final User user = new User();
    BeanUtils.copyProperties(dto, user);
    System.out.println(dto);
    System.out.println(user);
    return user;
  }


  public static UserTransaction toEntity(final TransactionRequestDto requestDto) {
    final UserTransaction ut = new UserTransaction();
    ut.setUserId(requestDto.getUserId());
    ut.setAmount(requestDto.getAmount());
    ut.setTransactionDate(LocalDateTime.now());
    return ut;
  }

  public static TransactionResponseDto toDto(final TransactionRequestDto requestDto, final TransactionStatus transactionStatus) {
    final TransactionResponseDto responseDto = new TransactionResponseDto();
    responseDto.setAmount(requestDto.getAmount());
    responseDto.setUserId(requestDto.getUserId());
    responseDto.setStatus(transactionStatus);
    return responseDto;
  }
}
