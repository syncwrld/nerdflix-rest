package me.syncwrld.nerdflixapi;

import lombok.AccessLevel;
import lombok.Getter;
import me.syncwrld.nerdflixapi.listener.ShutdownListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NerdflixServerEngine {

    @Getter(AccessLevel.PUBLIC)
    private static NerdflixServerBootstrap serverBootstrap;

    @Getter(AccessLevel.PUBLIC)
    private static ConfigurableApplicationContext applicationContext;


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NerdflixServerEngine.class);
        app.addListeners(new ShutdownListener());
        applicationContext = app.run(args);

        serverBootstrap = new NerdflixServerBootstrap();
        serverBootstrap.startServer();
    }

}
