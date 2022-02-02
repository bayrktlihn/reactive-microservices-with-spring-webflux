package io.bayrktlihn.userservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TransactionRequestDto {

  private Integer userId;
  private Integer amount;
}
