package me.syncwrld.nerdflixapi.database.setup;

import me.syncwrld.booter.database.IdentifiableRepository;
import me.syncwrld.booter.database.connector.SimpleDatabaseConnector;
import me.syncwrld.booter.database.connector.sample.DatabaseType;
import me.syncwrld.nerdflixapi.database.ClassesDatabase;
import me.syncwrld.nerdflixapi.database.CoursesDatabase;
import me.syncwrld.nerdflixapi.database.LoginDatabase;
import me.syncwrld.nerdflixapi.holder.DatabaseHolder;
import me.syncwrld.nerdflixapi.server.ServerCore;

import java.io.File;
import java.util.HashSet;

public class DatabaseSetup {

    public static void loadAll(ServerCore serverCore) {
        loadLoginDatabase(serverCore);
        loadCoursesDatabase(serverCore);
        loadClassesDatabase(serverCore);

        HashSet<IdentifiableRepository> repositories = DatabaseHolder.REPOSITORIES;
        repositories.add(DatabaseHolder.LOGIN);
        repositories.add(DatabaseHolder.COURSES);
        repositories.add(DatabaseHolder.CLASSES);

        for (IdentifiableRepository repository : repositories) {
            repository.createTables();
        }
    }

    public static void loadLoginDatabase(ServerCore serverCore) {
        File sqliteFile = new File(serverCore.formatPath("@dir/database/users-login.db"));
        SimpleDatabaseConnector databaseConnector = new SimpleDatabaseConnector(DatabaseType.SQLITE, sqliteFile);

        if (!databaseConnector.connect()) {
            ServerCore.error("Failed to connect to login database.");
        } else {
            ServerCore.info("Login database setup successfully.");
        }

        DatabaseHolder.LOGIN = new LoginDatabase(databaseConnector.getConnection());
    }

    public static void loadClassesDatabase(ServerCore serverCore) {
        File sqliteFile = new File(serverCore.formatPath("@dir/database/classes.db"));
        SimpleDatabaseConnector databaseConnector = new SimpleDatabaseConnector(DatabaseType.SQLITE, sqliteFile);

        if (!databaseConnector.connect()) {
            ServerCore.error("Failed to connect to classes database.");
        } else {
            ServerCore.info("Classes database setup successfully.");
        }

        DatabaseHolder.CLASSES = new ClassesDatabase(databaseConnector.getConnection());
    }

    public static void loadCoursesDatabase(ServerCore serverCore) {
        File sqliteFile = new File(serverCore.formatPath("@dir/database/courses.db"));
        SimpleDatabaseConnector databaseConnector = new SimpleDatabaseConnector(DatabaseType.SQLITE, sqliteFile);

        if (!databaseConnector.connect()) {
            ServerCore.error("Failed to connect to courses database.");
        } else {
            ServerCore.info("Courses database setup successfully.");
        }

        DatabaseHolder.COURSES = new CoursesDatabase(databaseConnector.getConnection());
        CoursesDatabase coursesDatabase = DatabaseHolder.COURSES;
    }

}
