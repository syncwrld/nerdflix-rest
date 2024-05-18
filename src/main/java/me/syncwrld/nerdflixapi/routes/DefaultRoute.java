package me.syncwrld.nerdflixapi.routes;

import me.syncwrld.nerdflixapi.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DefaultRoute {

    @RequestMapping(value = "/")
    public String handleDefault() {
        return Constants.GSON.toJson(Map.of("appName", "Nerdflix API", "version", Constants.VERSION, "authors", Constants.AUTHORS));
    }

}
