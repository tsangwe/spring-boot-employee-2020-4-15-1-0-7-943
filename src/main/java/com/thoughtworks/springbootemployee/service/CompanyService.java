package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.getAllCompanies();
    }

    public List<Company> getCompaniesInRange(int page, int pageSize) {
        int skipNumberOfRecords = page == 1 ? 0 : (page - 1) * pageSize;
        return companyRepository.getCompaniesInRange(skipNumberOfRecords, pageSize);
    }

    public Company getCompanyById(int companyId) {
        return companyRepository.getCompanyById(companyId);
    }

    public List<Employee> getEmployeesInCompany(int companyId) {
        return companyRepository.getEmployeesInCompany(companyId);
    }

    public Company addCompany(Company company) {
        companyRepository.addCompany(company);
        return company;
    }

    public Company updateCompany(int companyId, Company newCompany) {
        companyRepository.updateCompany(companyId, newCompany);
        return newCompany;
    }

    public void deleteAllEmployeesInCompany(int companyId) {
        companyRepository.deleteAllEmployeesInCompany(companyId);
    }
}
