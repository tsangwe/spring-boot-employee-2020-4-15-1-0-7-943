package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompaniesInRange(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company getCompanyById(int companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeesInCompany(int companyId) {
        return Objects.requireNonNull(companyRepository.findById(companyId).orElse(null)).getEmployees();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(int companyId, Company newCompany) {
        Company companyToUpdate = companyRepository.getOne(companyId);

        if (newCompany.getCompanyName() != null) {
            companyToUpdate.setCompanyName(newCompany.getCompanyName());
        }
        if (newCompany.getEmployeesNumber() != null) {
            companyToUpdate.setEmployeesNumber(newCompany.getEmployeesNumber());
        }
        if (newCompany.getEmployees() != null) {
            companyToUpdate.setEmployees(newCompany.getEmployees());
        }

        return companyRepository.save(companyToUpdate);
    }

    public void deleteAllEmployeesInCompany(int companyId) {
        companyRepository.deleteById(companyId);
    }
}
