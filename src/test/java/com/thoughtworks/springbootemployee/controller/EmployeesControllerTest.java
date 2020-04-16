package com.thoughtworks.springbootemployee.controller;

import com.google.gson.Gson;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeesService;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class EmployeesControllerTest {

    @Autowired
    private EmployeesController employeesController;
    @Autowired
    private EmployeesService employeesService;
    private Gson gson;
    private static final String DEFAULT_PATH_PREFIX = "/api/employee";
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;


    @Before
    public void setup() {
        gson = new Gson();
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(employeesController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        employee1 = new Employee(1, "Wesley", 10, "Male", 100);
        employee2 = new Employee(2, "Lusi", 11, "Female", 1000);
        employee3 = new Employee(3, "Harvey", 12, "Male", 0);
        employee4 = new Employee(4, "Angel", 13, "Female", 1000);

        employeesService.addEmployee(employee1);
        employeesService.addEmployee(employee2);
        employeesService.addEmployee(employee3);
    }

    @Test
    public void should_return_all_Employees() {
        MockMvcResponse response = given()
                .when()
                .get(DEFAULT_PATH_PREFIX + "/employees");
        Assert.assertEquals(200, response.getStatusCode());
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        List<Employee> expectedOutput = new ArrayList<>();
        expectedOutput.add(employee1);
        expectedOutput.add(employee2);
        expectedOutput.add(employee3);
        Assert.assertEquals(gson.toJson(expectedOutput), gson.toJson(employees));
    }

    @Test
    public void should_return_page_of_employees_when_employees_with_page_params() {
        MockMvcResponse response = given()
                .params(new HashMap<String, Integer>() {{
                    put("page", 2);
                    put("pageSize", 2);
                }})
                .when()
                .get(DEFAULT_PATH_PREFIX + "/employees");
        Assert.assertEquals(200, response.getStatusCode());
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.size());
    }

    @Test
    public void should_return_male_employees_when_companies_with_gender_param() {
        MockMvcResponse response = given()
                .params("gender", "Male")
                .when()
                .get(DEFAULT_PATH_PREFIX + "/employees");
        Assert.assertEquals(200, response.getStatusCode());
        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, employees.size());
    }

    @Test
    public void should_return_company_when_provide_company_with_id() {
        MockMvcResponse response = given()
                .when()
                .get(DEFAULT_PATH_PREFIX + "/employees/1");
        Assert.assertEquals(200, response.getStatusCode());
        String employee = response.getBody().asString();
        Assert.assertEquals(gson.toJson(employee1), employee);
    }
}