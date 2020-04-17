package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String companyName;
    private Integer employeesNumber;
    @OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
    private List<Employee> employees;
}
