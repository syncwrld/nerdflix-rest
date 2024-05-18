package me.syncwrld.nerdflixapi.management;

import me.syncwrld.nerdflixapi.holder.LoaderHolder;

public class AuthorizationManager {

  public boolean checkAuthorization(String apiKey) {
    return LoaderHolder.API_KEY_LOADER.getApiKeys().contains(apiKey);
  }
}
