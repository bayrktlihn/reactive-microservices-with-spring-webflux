package io.bayrktlihn.webfluxdemo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InputFailedValidationResponse {

  private int errorCode;
  private int input;
  private String message;

}
