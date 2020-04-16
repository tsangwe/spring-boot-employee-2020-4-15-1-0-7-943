package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;
    @Autowired
    private CompanyService companyService;
    private static final String DEFAULT_PATH_PREFIX = "/api/company";

    @Before
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(companyController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        companyService.addCompany(new Company(1, "Big Company", 3,
                Stream.of(new Employee(1, "Wesley", 10, "Male", 1),
                        new Employee(2, "Andy", 11, "Male", 5),
                        new Employee(3, "Angle", 9, "Female", 5000))
                .collect(Collectors.toList())));

        companyService.addCompany(new Company(2, "Small Company", 2,
                Stream.of(new Employee(1, "Suki", 15, "Female", 1000),
                        new Employee(2, "Junie", 16, "Female", 5000))
                        .collect(Collectors.toList())));

        companyService.addCompany(new Company(3, "Small Potato", 1,
                Stream.of(new Employee(1, "Tony", 30, "Female", 0))
                        .collect(Collectors.toList())));
    }

    @Test
    public void should_return_all_companies_when_companies_with_no_params() {
        MockMvcResponse response = given()
                .when()
                .get(DEFAULT_PATH_PREFIX + "/companies");
        Assert.assertEquals(200, response.getStatusCode());
        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, companies.size());
    }

    @Test
    public void should_return_page_of_companies_when_companies_with_params() {
        MockMvcResponse response = given()
                .params(new HashMap<String, Integer>() {{
                    put("page", 2);
                    put("pageSize", 2);
                }})
                .when()
                .get(DEFAULT_PATH_PREFIX + "/companies");
        Assert.assertEquals(200, response.getStatusCode());
        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, companies.size());
    }
}