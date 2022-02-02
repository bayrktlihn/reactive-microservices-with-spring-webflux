package io.bayrktlihn.webfluxdemo.exceptionhandler;

import io.bayrktlihn.webfluxdemo.dto.InputFailedValidationResponse;
import io.bayrktlihn.webfluxdemo.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

  @ExceptionHandler(InputValidationException.class)
  public ResponseEntity<InputFailedValidationResponse> handleException(final InputValidationException ex) {
    final InputFailedValidationResponse response = new InputFailedValidationResponse();
    response.setErrorCode(ex.getErrorCode());
    response.setInput(ex.getInput());
    response.setMessage(ex.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

}
