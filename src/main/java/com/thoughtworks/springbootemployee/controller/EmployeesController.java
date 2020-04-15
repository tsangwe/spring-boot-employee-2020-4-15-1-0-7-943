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
    public List<Employee> getEmployees(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) String gender) {
        if (page == null || pageSize == null) {
            if (gender == null) {
                return employeesService.getAllCompanies();
            }
            return employeesService.getEmployeesByGender(gender);
        }
        return employeesService.getEmployeesInRange(page, pageSize);
    }

    @GetMapping(path = "/employees/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeesService.getEmployeeById(id);
    }

    @PostMapping(path = "/employees")
    public void addEmployee(@RequestBody Employee employee) {
        employeesService.addEmployee(employee);
    }

    @PutMapping(path = "/employees/{id}")
    public void updateEmployee(@PathVariable int id, @RequestParam String newName) {
        employeesService.updateEmployee(id, newName);
    }

    @DeleteMapping(path = "/employees/{id}")
    public void deleteEmployee(@PathVariable int id) {
    }

}
