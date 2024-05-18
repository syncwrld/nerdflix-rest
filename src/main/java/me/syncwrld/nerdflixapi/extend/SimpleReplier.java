package me.syncwrld.nerdflixapi.extend;

import me.syncwrld.nerdflixapi.Constants;

public class SimpleReplier {

    public String asJson(Object object) {
        return Constants.GSON.toJson(object, object.getClass());
    }

    public <T> T asObject(String json, Class<T> clazz) {
        return Constants.GSON.fromJson(json, clazz);
    }

}
