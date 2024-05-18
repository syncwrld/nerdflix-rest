package me.syncwrld.nerdflixapi.database;

import lombok.Getter;
import me.syncwrld.booter.database.DatabaseHelper;
import me.syncwrld.booter.database.IdentifiableRepository;
import me.syncwrld.booter.database.util.Async;
import me.syncwrld.nerdflixapi.Constants;
import me.syncwrld.nerdflixapi.model.course.Course;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class CoursesDatabase implements IdentifiableRepository, DatabaseHelper {

    private final Connection connection;

    /*
    Queries
     */
    private final String CREATE_TABLE_QUERY = "create table if not exists courses (courseID VARCHAR(8) PRIMARY KEY, props TEXT, creationTime DATETIME)";
    private final String INSERT_COURSE_QUERY = "insert into courses (courseID, props, creationTime) values (?, ?, ?)";
    private final String SELECT_COURSE_QUERY = "select * from courses where courseID = ?";
    private final String DELETE_COURSE_QUERY = "delete from courses where courseID = ?";
    private final String UPDATE_COURSE_QUERY = "update courses set props = ? where courseID = ?";
    private final String GET_PROPS_QUERY = "select props from courses where courseID = ?";
    private final String SELECT_LIKE_COURSE_ID_QUERY = "select * from courses where courseID like ?";
    private final String SELECT_ALL_COURSES_QUERY = "select * from courses";

    public CoursesDatabase(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getName() {
        return "courses";
    }

    @Override
    public boolean hasMoreThanOneTable() {
        return false;
    }

    @Override
    public void createTables() {
        PreparedStatement prepare = this.prepare(this.connection, CREATE_TABLE_QUERY);
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

    public Course findById(String id) {
        PreparedStatement prepare = this.prepare(this.connection, SELECT_COURSE_QUERY);
        try {
            prepare.setString(1, id);
            ResultSet resultSet = prepare.executeQuery();
            if (resultSet.next()) {
                return Constants.GSON.fromJson(resultSet.getString("props"), Course.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertCourse(Course course) {
        PreparedStatement prepare = this.prepare(this.connection, INSERT_COURSE_QUERY);
        try {
            prepare.setString(1, course.getId());
            prepare.setString(2, Constants.GSON.toJson(course));
            prepare.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(Course course) {
        PreparedStatement prepare = this.prepare(this.connection, UPDATE_COURSE_QUERY);
        try {
            prepare.setString(1, Constants.GSON.toJson(course));
            prepare.setString(2, course.getId());
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(Course course) {
        PreparedStatement prepare = this.prepare(this.connection, DELETE_COURSE_QUERY);
        try {
            prepare.setString(1, course.getId());
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(String id) {
        PreparedStatement prepare = this.prepare(this.connection, DELETE_COURSE_QUERY);
        try {
            prepare.setString(1, id);
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Course> findLike(String partialId) {
        PreparedStatement prepare = this.prepare(this.connection, SELECT_LIKE_COURSE_ID_QUERY);
        try {
            prepare.setString(1, "%" + partialId + "%");
            ResultSet resultSet = prepare.executeQuery();
            Set<Course> courses = new java.util.HashSet<>(Set.of());
            while (resultSet.next()) {
                Course course = Constants.GSON.fromJson(resultSet.getString("props"), Course.class);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Course> getAllCourses() {
        PreparedStatement prepare = this.prepare(this.connection, SELECT_ALL_COURSES_QUERY);
        try {
            ResultSet resultSet = prepare.executeQuery();
            List<Course> courses = new java.util.ArrayList<>(List.of());
            while (resultSet.next()) {
                Course course = Constants.GSON.fromJson(resultSet.getString("props"), Course.class);
                courses.add(course);
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Integer> getTableIDs() {
        return Map.of("courses", 0);
    }

}
