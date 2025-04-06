package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.CourseDTO;
import com.jobskillportal.jobskillportalbackend.entity.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(CourseDTO dto);

    Course updateCourse(Long id, CourseDTO dto);

    List<Course> getAllCourses();

    Course togglePublished(Long id);
}
