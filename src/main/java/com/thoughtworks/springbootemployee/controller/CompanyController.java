package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping(path = "/companies")
    public List<Company> getCompanies(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) {
        if (page == null || pageSize == null) {
            return companyService.getAllCompanies();
        }
        return companyService.getCompaniesInRange(page, pageSize);
    }

    @GetMapping(path = "/companies/{companyId}")
    public Company getCompanyById(@PathVariable int companyId) {
        return companyService.getCompanyById(companyId);
    }

    @GetMapping(path = "/companies/{companyId}/employees")
    public List<Employee> getEmployeesInCompany(@PathVariable int companyId) {
        return companyService.getEmployeesInCompany(companyId);
    }

    @PostMapping(path = "/companies")
    public void addCompany(@RequestBody Company company) {
        companyService.addCompany(company);
    }

    @PutMapping(path = "/companies/{companyId}")
    public void updateCompanyName(@PathVariable int companyId, @RequestBody Company newCompany) {
        companyService.updateCompanyName(companyId, newCompany);
    }

    @DeleteMapping(path = "/companies/{companyId}")
    public void deleteAllEmployeesInCompany(@PathVariable int companyId) {
        companyService.deleteAllEmployeesInCompany(companyId);
    }
}
