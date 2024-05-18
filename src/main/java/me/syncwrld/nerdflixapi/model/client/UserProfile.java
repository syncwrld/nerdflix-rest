package me.syncwrld.nerdflixapi.model.client;

import lombok.Data;
import me.syncwrld.nerdflixapi.model.authentication.User;

@Data
public class UserProfile {
    private final User user;
    private final String imageAvatarURL;
    private final String imageBannerURL;
    private final String bio;
    private final String localization;
}
