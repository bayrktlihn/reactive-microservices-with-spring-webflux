package io.bayrktlihn.userservice.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserTransaction {

  @Id
  private Integer id;
  private Integer userId;
  private Integer amount;
  private LocalDateTime transactionDate;

}
