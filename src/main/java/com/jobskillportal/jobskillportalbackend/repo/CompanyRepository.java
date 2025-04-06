package com.jobskillportal.jobskillportalbackend.repo;

import com.jobskillportal.jobskillportalbackend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByUserId(Long userId);
}
