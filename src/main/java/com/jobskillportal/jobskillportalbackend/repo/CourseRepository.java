package com.jobskillportal.jobskillportalbackend.repo;

import com.jobskillportal.jobskillportalbackend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
