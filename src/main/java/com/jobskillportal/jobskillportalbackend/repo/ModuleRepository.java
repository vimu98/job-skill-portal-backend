package com.jobskillportal.jobskillportalbackend.repo;

import com.jobskillportal.jobskillportalbackend.dto.ModuleDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jobskillportal.jobskillportalbackend.entity.Module;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByCourseId(Long courseId);
}
