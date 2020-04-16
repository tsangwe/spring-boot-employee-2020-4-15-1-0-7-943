package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllCompanies() {
        return employeeRepository.getAllCompanies();
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.getEmployeesByGender(gender);
    }

    public List<Employee> getEmployeesInRange(Integer page, Integer pageSize) {
        int skipNumber = page == 1 ? 0 : (page - 1) * pageSize;
        return employeeRepository.getEmployeesInRange(skipNumber, pageSize);
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.getEmployeeById(id);
    }

    public Employee addEmployee(Employee employee) {
        employeeRepository.addEmployee(employee);
        return employee;
    }

    public Employee updateEmployee(int id, Employee newEmployee) {
        employeeRepository.updateEmployee(id, newEmployee);
        return newEmployee;
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteEmployee(id);
    }
}
