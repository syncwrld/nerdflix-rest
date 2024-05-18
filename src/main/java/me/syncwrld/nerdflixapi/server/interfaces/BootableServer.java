package me.syncwrld.nerdflixapi.server.interfaces;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public interface BootableServer {
  void enable();

  void disable();

  default void restart() {
    disable();
    Executors.newScheduledThreadPool(1).schedule(this::enable, 2, TimeUnit.SECONDS);
  }
}
