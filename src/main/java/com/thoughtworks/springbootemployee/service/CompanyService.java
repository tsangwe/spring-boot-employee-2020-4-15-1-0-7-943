package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private List<Company> companies = new ArrayList<>();


    public List<Company> getAllCompanies() {
        return companies;
    }

    public List<Company> getCompaniesInRange(int page, int pageSize) {
        return companies.stream().limit(page * pageSize).collect(Collectors.toList());
    }

    public Company getCompanyById(int companyId) {
        return companies.stream().filter(company -> company.getCompanyId() == companyId).findFirst().orElse(null);
    }
}
