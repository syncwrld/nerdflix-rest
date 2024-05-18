package me.syncwrld.nerdflixapi.management;

import me.syncwrld.nerdflixapi.server.ServerCore;

public class DefaultFileManager {

  private final ServerCore serverCore;

  public DefaultFileManager(ServerCore serverCore) {
    this.serverCore = serverCore;
  }

  public void saveDefaults() {
    this.serverCore.saveResource("authentication/api-keys.json");
    this.serverCore.saveResource("settings/database.yml");
    this.serverCore.saveResource("settings/options.yml");
    this.serverCore.saveResource("profiles/icons/nothing_here.txt");
    this.serverCore.saveResource("profiles/info/nothing_here.txt");
  }
}
