package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class EmployeesController {
    @Autowired
    private EmployeesService employeesService;

    @GetMapping(path = "/employees")
    public List<Employee> getEmployees() {
        return null;
    }

    @GetMapping(path = "/employees/{id}")
    public Employee getEmployee(@PathVariable String id) {
        return null;
    }

    @GetMapping(path = "/employees")
    public List<Employee> getEmployeesInRange(@RequestParam int page, @RequestParam int pageSize) {
        return null;
    }

    @GetMapping(path = "/employees")
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return null;
    }

    @PostMapping(path = "/employees")
    public void addEmployee() {
    }

    @PutMapping(path = "/employees/{id}")
    public void updateEmployee(@PathVariable String id) {
    }

    @DeleteMapping(path = "/employees/{id}")
    public void deleteEmployee(@PathVariable String id) {
    }

}
