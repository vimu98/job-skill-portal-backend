package com.jobskillportal.jobskillportalbackend.service.impl;

import com.jobskillportal.jobskillportalbackend.dto.CompanyDTO;
import com.jobskillportal.jobskillportalbackend.entity.Company;
import com.jobskillportal.jobskillportalbackend.repo.CompanyRepository;
import com.jobskillportal.jobskillportalbackend.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CompanyDTO> getCompanyById(Long id) {
        return companyRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<CompanyDTO> getCompaniesByUserId(Long userId) {
        return companyRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = convertToEntity(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return convertToDTO(savedCompany);
    }

    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Optional<Company> existingCompanyOpt = companyRepository.findById(id);
        if (existingCompanyOpt.isPresent()) {
            Company existingCompany = existingCompanyOpt.get();
            existingCompany.setUserId(companyDTO.getUserId());
            existingCompany.setName(companyDTO.getName());
            existingCompany.setLocation(companyDTO.getLocation());
            Company updatedCompany = companyRepository.save(existingCompany);
            return convertToDTO(updatedCompany);
        }
        return null; // Handle better with exception handling
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    private CompanyDTO convertToDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getUserId(), company.getName(), company.getLocation());
    }

    private Company convertToEntity(CompanyDTO companyDTO) {
        return new Company(companyDTO.getId(), companyDTO.getUserId(), companyDTO.getName(), companyDTO.getLocation());
    }
}
