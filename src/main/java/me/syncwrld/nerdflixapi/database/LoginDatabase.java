package me.syncwrld.nerdflixapi.database;

import lombok.AccessLevel;
import lombok.Getter;
import me.syncwrld.booter.database.DatabaseHelper;
import me.syncwrld.booter.database.IdentifiableRepository;
import me.syncwrld.booter.database.util.Async;
import me.syncwrld.nerdflixapi.Constants;
import me.syncwrld.nerdflixapi.model.authentication.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
public class LoginDatabase implements IdentifiableRepository, DatabaseHelper {

    private final Connection connection;

    public LoginDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "users";
    }

    @Override
    public boolean hasMoreThanOneTable() {
        return false;
    }

    @Override
    public void createTables() {
        PreparedStatement prepare = this.prepare(this.connection, "create table if not exists users (uuid VARCHAR(42) PRIMARY KEY, data TEXT)");

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
            throw new RuntimeException(e);
        }
    }

    public User getByUUID(UUID uuid) {
        PreparedStatement prepare = this.prepare(this.connection, "select * from users where uuid = ?");
        try {
            prepare.setString(1, uuid.toString());
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                return Constants.GSON.fromJson(resultSet.getString("data"), User.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertUser(UUID uuid, User user) {
        PreparedStatement prepare = this.prepare(this.connection, "insert into users (uuid, data) values (?, ?)");
        try {
            prepare.setString(1, uuid.toString());
            prepare.setString(2, Constants.GSON.toJson(user, User.class));
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Integer> getTableIDs() {
        return Map.of("users", 0);
    }

    public HashSet<User> getAllUsers() {
        PreparedStatement prepare = this.prepare(this.connection, "select * from users");
        HashSet<User> users = new HashSet<>();
        try {
            ResultSet resultSet = prepare.executeQuery();
            while (resultSet.next()) {
                users.add(Constants.GSON.fromJson(resultSet.getString("data"), User.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
