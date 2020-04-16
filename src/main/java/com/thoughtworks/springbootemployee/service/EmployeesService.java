package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeesService {
    private List<Employee> employees = new ArrayList<>();


    public List<Employee> getAllCompanies() {
        return employees;
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesInRange(Integer page, Integer pageSize) {
        return employees.stream()
                .limit(page * pageSize)
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void updateEmployee(int id, Employee newEmployee) {
        employees.stream()
                .filter(currentEmployee -> currentEmployee.getId() == id)
                .collect(Collectors.toList())
                .replaceAll(currentEmployee -> currentEmployee = newEmployee);
    }

    public void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }
}
