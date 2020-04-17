package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public List<Employee> getEmployeesInRange(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(int id, Employee newEmployee) {
        Employee employeeToUpdate = employeeRepository.getOne(id);

        if (newEmployee.getName() != null) {
            employeeToUpdate.setName(newEmployee.getName());
        }
        if (newEmployee.getAge() != null) {
            employeeToUpdate.setAge(newEmployee.getAge());
        }
        if (newEmployee.getGender() != null) {
            employeeToUpdate.setGender(newEmployee.getGender());
        }
        if (newEmployee.getGender() != null) {
            employeeToUpdate.setGender(newEmployee.getGender());
        }
        if (newEmployee.getSalary() != null) {
            employeeToUpdate.setSalary(newEmployee.getSalary());
        }

        return employeeRepository.save(employeeToUpdate);
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}
