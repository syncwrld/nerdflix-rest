package me.syncwrld.nerdflixapi.memory.loader;

import me.syncwrld.nerdflixapi.database.LoginDatabase;
import me.syncwrld.nerdflixapi.holder.DatabaseHolder;
import me.syncwrld.nerdflixapi.holder.MemoryHolder;
import me.syncwrld.nerdflixapi.memory.UserMemory;
import me.syncwrld.nerdflixapi.server.ServerCore;

public class MemoryToDatabaseLoader {

    public static MemoryToDatabaseLoader INSTANCE = new MemoryToDatabaseLoader();

    public void load() {
        loadUsers();
    }

    private void loadUsers() {
        ServerCore.info("Loading users from memory to database...");

        UserMemory userMemory = MemoryHolder.USER;
        LoginDatabase loginDatabase = DatabaseHolder.LOGIN;
        userMemory.getUsers().forEach(loginDatabase::insertUser);

        ServerCore.info("Loaded " + userMemory.getUsers().size() + " users in memory to database...");
    }

}
