package io.bayrktlihn.userservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table("users")
public class User {

  @Id
  private Integer id;
  private String name;
  private Integer balance;
}
