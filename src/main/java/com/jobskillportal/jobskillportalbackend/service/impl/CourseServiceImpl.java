package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.CourseDTO;
import com.jobskillportal.jobskillportalbackend.entity.Course;
import com.jobskillportal.jobskillportalbackend.repo.CourseRepository;
import com.jobskillportal.jobskillportalbackend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepo;


    public Course createCourse(CourseDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory());
        course.setDuration(dto.getDuration());
        course.setSkillLevel(dto.getSkillLevel());
        course.setPublished(false);
        return courseRepo.save(course);
    }


    public Course updateCourse(Long id, CourseDTO dto) {
        Course course = courseRepo.findById(id).orElseThrow();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setCategory(dto.getCategory());
        course.setDuration(dto.getDuration());
        course.setSkillLevel(dto.getSkillLevel());
        return courseRepo.save(course);
    }


    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }


    public Course togglePublished(Long id) {
        Course course = courseRepo.findById(id).orElseThrow();
        course.setPublished(!course.isPublished());
        return courseRepo.save(course);
    }
}
