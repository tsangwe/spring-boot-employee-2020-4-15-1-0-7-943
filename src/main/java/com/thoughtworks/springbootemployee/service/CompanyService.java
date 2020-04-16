package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private List<Company> companies = new ArrayList<>();


    public List<Company> getAllCompanies() {
        return companies;
    }

    public List<Company> getCompaniesInRange(int page, int pageSize) {
        return companies.stream()
                .limit(page * pageSize)
                .collect(Collectors.toList());
    }

    public Company getCompanyById(int companyId) {
        return companies.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployeesInCompany(int companyId) {
        return Objects.requireNonNull(companies.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null))
                .getEmployees();
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    public void updateCompanyName(int companyId, Company newCompany) {
        companies.stream()
                .filter(currentCompany -> currentCompany.getCompanyId() == companyId)
                .collect(Collectors.toList())
                .replaceAll(currentCompany -> currentCompany = newCompany);
    }

    public void deleteAllEmployeesInCompany(int companyId) {
        Objects.requireNonNull(companies.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null))
                .setEmployees(new ArrayList<>());
    }
}
