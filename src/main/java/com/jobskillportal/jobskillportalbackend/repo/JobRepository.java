package com.jobskillportal.jobskillportalbackend.repo;

import com.jobskillportal.jobskillportalbackend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findBySkillsRequiredIn(List<String> skills);
}
