package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private int companyId;
    private String companyName;
    private int employeesNumber;
    private List<Employee> employees;
}
