package com.jobskillportal.jobskillportalbackend.service;

import com.jobskillportal.jobskillportalbackend.dto.CompanyDTO;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();

    Optional<CompanyDTO> getCompanyById(Long id);

    CompanyDTO createCompany(CompanyDTO companyDTO);

    List<CompanyDTO> getCompaniesByUserId(Long userId);

    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);

    void deleteCompany(Long id);
}
