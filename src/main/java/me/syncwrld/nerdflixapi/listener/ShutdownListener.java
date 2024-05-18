package me.syncwrld.nerdflixapi.listener;

import me.syncwrld.nerdflixapi.NerdflixServerEngine;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        NerdflixServerEngine.getServerBootstrap().disable();
    }
}
