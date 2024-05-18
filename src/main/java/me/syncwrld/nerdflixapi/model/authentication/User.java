package me.syncwrld.nerdflixapi.model.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import me.syncwrld.nerdflixapi.model.course.Course;

import java.time.Instant;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@ToString
public class User {

    @Expose
    @SerializedName("uuid")
    private final UUID uuid;

    @Expose
    @SerializedName("consistent")
    private final String consistentName;

    @Expose
    @SerializedName("email")
    private final String email;

    @Expose
    @SerializedName("encryptedPasskey")
    private final String password;

    @Expose
    @SerializedName("encryptionRandomSalt")
    private final String saltToken;

    @Expose
    @SerializedName("acquiredCourses")
    private final Collection<Course> acquiredCourses = new HashSet<>();

    @Expose
    @SerializedName("subscribedCourses")
    private final Set<Course> subscribedCourses = new HashSet<>();

    @Expose
    @SerializedName("completedCourses")
    private final Set<Course> completedCourses = new HashSet<>();

}
