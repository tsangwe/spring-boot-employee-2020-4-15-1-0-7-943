package com.thoughtworks.springbootemployee.model;

import lombok.Data;

import java.util.List;

@Data
public class Company {
    private int companyId;
    private String companyName;
    private int employeesNumber;
    private List<Employee> employees;
}
