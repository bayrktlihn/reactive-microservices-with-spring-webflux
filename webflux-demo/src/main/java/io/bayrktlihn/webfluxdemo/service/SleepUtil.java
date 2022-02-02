package io.bayrktlihn.webfluxdemo.service;

import java.util.concurrent.TimeUnit;

public class SleepUtil {

  public static void sleepSeconds(final int timeout) {
    try {
      TimeUnit.SECONDS.sleep(timeout);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
