package com.thoughtworks.springbootemployee.model;

import lombok.Data;

@Data
public class Employee {
    private int id;
    private String name;
    private int age;
    private String gender;
    private int salary;
}
