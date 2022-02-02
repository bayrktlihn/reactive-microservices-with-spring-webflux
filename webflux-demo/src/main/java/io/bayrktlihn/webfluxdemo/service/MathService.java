package io.bayrktlihn.webfluxdemo.service;

import io.bayrktlihn.webfluxdemo.dto.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class MathService {

  public Response findSquare(final int input) {
    return Response.create(input * input);
  }

  public List<Response> multiplicationTable(final int input) {
    return IntStream.rangeClosed(1, 10)
        .peek(i -> SleepUtil.sleepSeconds(1))
        .peek(i -> System.out.println("math-service processing : " + i))
        .mapToObj(i -> Response.create(i * input))
        .collect(Collectors.toList());
  }
}
