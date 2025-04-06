package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.LessonDTO;
import com.jobskillportal.jobskillportalbackend.entity.Lesson;

import java.util.List;

public interface LessonService {
    Lesson createLesson(LessonDTO dto);

    List<Lesson> getLessonsByModule(Long moduleId);
}
