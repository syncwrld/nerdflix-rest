package me.syncwrld.nerdflixapi.model.course;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import me.syncwrld.nerdflixapi.database.ClassesDatabase;
import me.syncwrld.nerdflixapi.database.CoursesDatabase;
import me.syncwrld.nerdflixapi.server.util.RandomString;
import org.springframework.lang.Nullable;

import java.util.ArrayList;

@Data
@ToString
public class Course {
    @Expose
    @SerializedName("courseId")
    private String id;

    @Expose
    @SerializedName("courseImageData")
    private String imageData;

    @Expose
    @SerializedName("courseName")
    private String name;

    @Expose
    @SerializedName("courseTitle")
    private String title;

    @Expose
    @SerializedName("courseDescription")
    private String description;

    @Expose
    @SerializedName("courseClass")
    @Nullable
    private ArrayList<CourseClass> classes;

    @Expose
    @SerializedName("complementaryGrade")
    private boolean isComplementary;

    @Expose
    @SerializedName("complementedCourse")
    private Course complementedCourse;

    @Expose
    @SerializedName("studentsAmount")
    private int students;

    public Course() {
    }

    public Course(String id, String name, String title, String description, ArrayList<CourseClass> classes, boolean isComplementary, Course complementedCourse, int students) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.classes = classes;
        this.isComplementary = isComplementary;
        this.complementedCourse = complementedCourse;
        this.students = students;
    }

    public void setId(String  id) {
        if (Strings.isNullOrEmpty(id)) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        this.id = id;
    }

    public void setStudents(int students) {
        if (students < 0) {
            throw new IllegalArgumentException("Students cannot be negative");
        }
        this.students = students;
    }

    public void setComplementedCourse(Course complementedCourse) {
        if (complementedCourse == this) {
            throw new IllegalArgumentException("Course cannot be complemented to itself");
        }
        this.complementedCourse = complementedCourse;
    }

    public void setupID(CoursesDatabase database) {
        RandomString randomString = new RandomString(true, true, true, false);
        String randomId = randomString.generate(8);

        // Check if generated id is unique
        if (database.findById(randomId) != null) {
            setupID(database);
        } else {
            this.id = randomId;
        }
    }

    public static void main(String[] args) {
        String baseURL = "https://nerdflix.top/curso/{0}/";

        RandomString randomString = new RandomString(true, true, true, false);

        for (int i = 0; i <= 10; i++) {
            String randomId = randomString.generate(16);
            System.out.println(baseURL.replace("{0}", randomId));
        }
    }
}
