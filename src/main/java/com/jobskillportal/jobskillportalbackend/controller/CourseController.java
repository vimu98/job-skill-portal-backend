package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.CourseDTO;
import com.jobskillportal.jobskillportalbackend.entity.Course;
import com.jobskillportal.jobskillportalbackend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody CourseDTO dto) {
        return courseService.createCourse(dto);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody CourseDTO dto) {
        return courseService.updateCourse(id, dto);
    }

    @GetMapping
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @PatchMapping("/{id}/toggle")
    public Course togglePublish(@PathVariable Long id) {
        return courseService.togglePublished(id);
    }
}
