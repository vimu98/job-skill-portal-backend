package com.jobskillportal.jobskillportalbackend.controller;

import com.jobskillportal.jobskillportalbackend.dto.LessonDTO;
import com.jobskillportal.jobskillportalbackend.entity.Lesson;
import com.jobskillportal.jobskillportalbackend.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public Lesson createLesson(@RequestBody LessonDTO dto) {
        return lessonService.createLesson(dto);
    }

    @GetMapping("/by-module/{moduleId}")
    public List<Lesson> getLessonsByModule(@PathVariable Long moduleId) {
        return lessonService.getLessonsByModule(moduleId);
    }
}