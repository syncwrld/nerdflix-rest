package me.syncwrld.nerdflixapi.memory;

import lombok.Getter;
import me.syncwrld.nerdflixapi.model.authentication.User;
import me.syncwrld.nerdflixapi.model.course.Course;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class CourseMemory {

    private final ConcurrentHashMap<String, Course> coursesMap = new ConcurrentHashMap<>();
    private final Set<Course> courses = ConcurrentHashMap.newKeySet();

    public void addCourse(Course course) {
        this.coursesMap.put(course.getId(), course);
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.coursesMap.remove(course.getId());
        this.courses.remove(course);
    }

    public Course getCourse(String id) {
        return this.coursesMap.get(id);
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void updateCourse(Course course) {
        this.coursesMap.put(course.getId(), course);
    }

    public void clear() {
        this.coursesMap.clear();
        this.courses.clear();
    }

    public void insert(Course course) {
        this.coursesMap.put(course.getId(), course);
        this.courses.add(course);
    }


}
