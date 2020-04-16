package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public List<Company> getAllCompanies() {
        return companies;
    }

    public List<Company> getCompaniesInRange(int skipNumberOfRecords, int pageSize) {
        return companies.stream()
                .skip(skipNumberOfRecords)
                .limit(pageSize)
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

    public void updateCompany(int companyId, Company newCompany) {
        Company company = Objects.requireNonNull(companies.stream()
                .filter(currentCompany -> currentCompany.getCompanyId() == companyId)
                .findFirst()
                .orElse(null));
        Collections.replaceAll(companies, company, newCompany);
    }

    public void deleteAllEmployeesInCompany(int companyId) {
        Objects.requireNonNull(companies.stream()
                .filter(company -> company.getCompanyId() == companyId)
                .findFirst()
                .orElse(null))
                .setEmployees(new ArrayList<>());
    }
}
