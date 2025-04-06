package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.ModuleDTO;
import com.jobskillportal.jobskillportalbackend.entity.Course;
import com.jobskillportal.jobskillportalbackend.repo.CourseRepository;
import com.jobskillportal.jobskillportalbackend.repo.ModuleRepository;
import com.jobskillportal.jobskillportalbackend.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.jobskillportal.jobskillportalbackend.entity.Module;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepo;
    private final CourseRepository courseRepo;


    public Module createModule(ModuleDTO dto) {
        Course course = courseRepo.findById(dto.getCourseId()).orElseThrow();
        Module module = new Module();
        module.setName(dto.getName());
        module.setCourse(course);
        return moduleRepo.save(module);
    }


    public List<Module> getModulesByCourse(Long courseId) {
        return moduleRepo.findByCourseId(courseId);
    }
}