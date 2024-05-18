package me.syncwrld.nerdflixapi.routes.course;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllCoursesRoute {

    @GetMapping("/api/courses/getAll")
    public String handleGetAllCourses() {
        return "[]";
    }

}
