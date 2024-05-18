package me.syncwrld.nerdflixapi.memory.loader;

import com.google.common.base.Stopwatch;
import me.syncwrld.nerdflixapi.database.LoginDatabase;
import me.syncwrld.nerdflixapi.holder.DatabaseHolder;
import me.syncwrld.nerdflixapi.holder.MemoryHolder;
import me.syncwrld.nerdflixapi.memory.UserMemory;
import me.syncwrld.nerdflixapi.model.authentication.User;
import me.syncwrld.nerdflixapi.server.ServerCore;

import java.util.HashSet;

public class DatabaseToMemoryLoader {

    public static DatabaseToMemoryLoader INSTANCE = new DatabaseToMemoryLoader();

    public void load() {
        this.loadUsers();
    }

    private void loadUsers() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        UserMemory userMemory = MemoryHolder.USER;
        LoginDatabase loginDatabase = DatabaseHolder.LOGIN;

        HashSet<User> allUsers = loginDatabase.getAllUsers();
        ServerCore.info("Loading " + allUsers.size() + " users from database to cache...");

        for (User user : allUsers) {
            userMemory.insertUser(user.getUuid(), user);
        }

        ServerCore.info("Loaded " + allUsers.size() + " users in " + stopwatch.stop() + "!");
    }

    private void loadCourses() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        UserMemory userMemory = MemoryHolder.USER;
        LoginDatabase loginDatabase = DatabaseHolder.LOGIN;

        HashSet<User> allUsers = loginDatabase.getAllUsers();
        ServerCore.info("Loading " + allUsers.size() + " users from database to cache...");

        for (User user : allUsers) {
            userMemory.insertUser(user.getUuid(), user);
        }

        ServerCore.info("Loaded " + allUsers.size() + " users in " + stopwatch.stop() + "!");
    }

}
