package me.syncwrld.nerdflixapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public static final String VERSION = "0.0.1-SNAPSHOT";
  public static final String[] AUTHORS = new String[] {"isaac"};
}
