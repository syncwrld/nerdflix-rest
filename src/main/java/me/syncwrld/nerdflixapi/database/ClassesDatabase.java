package me.syncwrld.nerdflixapi.database;

import lombok.AccessLevel;
import lombok.Getter;
import me.syncwrld.booter.Constants;
import me.syncwrld.booter.database.DatabaseHelper;
import me.syncwrld.booter.database.IdentifiableRepository;
import me.syncwrld.booter.database.util.Async;
import me.syncwrld.nerdflixapi.model.course.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Getter(AccessLevel.PUBLIC)
public class ClassesDatabase implements IdentifiableRepository, DatabaseHelper {

    private final Connection connection;

    public ClassesDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "classes";
    }

    @Override
    public boolean hasMoreThanOneTable() {
        return false;
    }

    @Override
    public void createTables() {
        PreparedStatement prepare = this.prepare(this.connection, "create table if not exists classes (classID TEXT PRIMARY KEY, data TEXT, creationTime TEXT)");
        try {
            Async.run(() -> {
                try {
                    prepare.executeUpdate();
                    prepare.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Integer> getTableIDs() {
        return Map.of("classes", 0);
    }

    public Course findById(String id) {
        PreparedStatement prepare = this.prepare(this.connection, "select * from classes where classID = ?");
        try {
            prepare.setString(1, id);
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                return Constants.GSON.fromJson(resultSet.getString("data"), Course.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
