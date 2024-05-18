package me.syncwrld.nerdflixapi.model.course;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Conditional;

import java.util.UUID;

@Data
public class CourseClass {
    @Expose @SerializedName("uuid")
    @NotNull
    private final UUID id = UUID.randomUUID();

    @Expose
    @SerializedName("classOrder")
    private final int order;

    @Expose
    @SerializedName("belongingCourse")
    private final Course course;

    @Expose
    @SerializedName("classTitle")
    private final String title;

    @Expose
    @SerializedName("classDescription")
    private final String description;
}
