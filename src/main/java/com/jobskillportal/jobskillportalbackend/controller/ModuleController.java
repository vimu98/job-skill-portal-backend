package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.ModuleDTO;
import com.jobskillportal.jobskillportalbackend.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.jobskillportal.jobskillportalbackend.entity.Module;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    public Module createModule(@RequestBody ModuleDTO dto) {
        return moduleService.createModule(dto);
    }

    @GetMapping("/by-course/{courseId}")
    public List<Module> getModulesByCourse(@PathVariable Long courseId) {
        return moduleService.getModulesByCourse(courseId);
    }
}