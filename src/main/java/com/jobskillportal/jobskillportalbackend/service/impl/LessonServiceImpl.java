package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.LessonDTO;
import com.jobskillportal.jobskillportalbackend.entity.Lesson;
import com.jobskillportal.jobskillportalbackend.repo.LessonRepository;
import com.jobskillportal.jobskillportalbackend.repo.ModuleRepository;
import com.jobskillportal.jobskillportalbackend.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.jobskillportal.jobskillportalbackend.entity.Module;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepo;
    private final ModuleRepository moduleRepo;


    public Lesson createLesson(LessonDTO dto) {
        Module module = moduleRepo.findById(dto.getModuleId()).orElseThrow();
        Lesson lesson = new Lesson();
        lesson.setTitle(dto.getTitle());
        lesson.setContent(dto.getContent());
        lesson.setVideoUrl(dto.getVideoUrl());
        lesson.setFileUrl(dto.getFileUrl());
        lesson.setModule(module);
        return lessonRepo.save(lesson);
    }


    public List<Lesson> getLessonsByModule(Long moduleId) {
        return lessonRepo.findByModuleId(moduleId);
    }
}