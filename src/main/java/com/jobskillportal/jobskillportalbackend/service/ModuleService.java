package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.ModuleDTO;
import com.jobskillportal.jobskillportalbackend.entity.Module;

import java.util.List;

public interface ModuleService {
    Module createModule(ModuleDTO dto);

    List<Module> getModulesByCourse(Long courseId);
}
