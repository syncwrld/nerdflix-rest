package me.syncwrld.nerdflixapi.auth;

import java.io.File;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.Getter;
import me.syncwrld.booter.CommonPurposeClass;
import me.syncwrld.booter.common.tool.JsonParser;
import me.syncwrld.booter.libs.google.gson.JsonArray;
import me.syncwrld.booter.libs.google.gson.JsonElement;
import me.syncwrld.nerdflixapi.NerdflixServerEngine;

@Getter(AccessLevel.PUBLIC)
public class ApiKeyLoader extends CommonPurposeClass {

  private final HashSet<String> apiKeys = new HashSet<String>();

  public File getFile() {
    return new File(formatPath("@dir/authentication/api-keys.json"));
  }

  public void saveFile() {
    this.saveFile(
        getFile().getAbsolutePath(), "authentication/api-keys.json", NerdflixServerEngine.class);
  }

  public void load() {
    JsonParser jsonParser = new JsonParser();
    JsonArray array = jsonParser.getArray(getFile());

    this.apiKeys.clear();

    for (JsonElement jsonElement : array) {
      String apiKey = jsonElement.getAsString();
      apiKeys.add(apiKey);
    }
  }
}
