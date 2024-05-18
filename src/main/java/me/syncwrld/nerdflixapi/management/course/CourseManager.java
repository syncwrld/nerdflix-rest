package me.syncwrld.nerdflixapi.management.course;

import me.syncwrld.nerdflixapi.database.CoursesDatabase;
import me.syncwrld.nerdflixapi.model.course.Course;
import me.syncwrld.nerdflixapi.model.course.CourseClass;

import java.util.ArrayList;

public class CourseManager {

    private final CoursesDatabase coursesDatabase;

    public CourseManager(CoursesDatabase coursesDatabase) {
        this.coursesDatabase = coursesDatabase;
    }

    public Course create(String name, String description, String imageData, ArrayList<CourseClass> courseClasses) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setClasses(courseClasses);
        course.setupID(this.coursesDatabase);
        course.setImageData(imageData);

        course.setStudents(0);
        course.setComplementedCourse(null);
        course.setComplementary(false);

        this.coursesDatabase.insertCourse(course);

        return course;
    }

}
