package me.syncwrld.nerdflixapi.memory;

import lombok.Getter;
import me.syncwrld.nerdflixapi.model.authentication.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UserMemory {

    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    public void insertUser(UUID uuid, User user) {
        this.users.putIfAbsent(uuid, user);
    }

    public void updateUser(UUID uuid, User user) {
        this.users.put(uuid, user);
    }

    public User findByUUID(UUID uuid) {
        return this.users.get(uuid);
    }

    public User findByEmail(String email) {
        return this.users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    public User findByConsistentName(String consistentName) {
        return this.users.values().stream().filter(user -> user.getConsistentName().equals(consistentName)).findFirst().orElse(null);
    }

}
