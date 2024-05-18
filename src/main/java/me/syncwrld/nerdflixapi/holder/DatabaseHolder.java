package me.syncwrld.nerdflixapi.holder;

import me.syncwrld.booter.database.IdentifiableRepository;
import me.syncwrld.nerdflixapi.database.ClassesDatabase;
import me.syncwrld.nerdflixapi.database.CoursesDatabase;
import me.syncwrld.nerdflixapi.database.LoginDatabase;

import java.util.HashSet;
import java.util.Set;

public class DatabaseHolder {
    public static LoginDatabase LOGIN;
    public static ClassesDatabase CLASSES;
    public static CoursesDatabase COURSES;
    public static HashSet<IdentifiableRepository> REPOSITORIES = new HashSet<>();
}
