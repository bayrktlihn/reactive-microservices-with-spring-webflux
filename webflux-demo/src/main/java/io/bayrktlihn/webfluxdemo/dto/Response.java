package io.bayrktlihn.webfluxdemo.dto;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Response {

  private Date date = new Date();
  private int output;

  private Response(final int output) {
    this.output = output;
  }

  public static Response create(final int output) {
    return new Response(output);
  }
}
