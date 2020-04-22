package com.thoughtworks.springbootemployee.controller;

import com.google.gson.Gson;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompanyControllerTest {
    @MockBean
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyController companyController;
    private Gson gson;
    private static final String DEFAULT_PATH_PREFIX = "/api/company";
    private Company company1;
    private Company company2;
    private Company company3;
    private Company company4;


    @Before
    public void setup() {
        gson = new Gson();
        RestAssuredMockMvc.standaloneSetup(companyController);

        company1 = new Company(1, "Big Company", 3,
                Stream.of(new Employee(1, "Wesley", 10, "Male", 1, 1),
                        new Employee(2, "Andy", 11, "Male", 5, 1),
                        new Employee(3, "Angle", 9, "Female", 5000, 1))
                        .collect(Collectors.toList()));
        company2 = new Company(2, "Small Company", 2,
                Stream.of(new Employee(1, "Suki", 15, "Female", 1000, 2),
                        new Employee(2, "Junie", 16, "Female", 5000, 2))
                        .collect(Collectors.toList()));
        company3 = new Company(3, "Small Potato", 1,
                Stream.of(new Employee(1, "Tony", 30, "Female", 0, 3))
                        .collect(Collectors.toList()));
        company4 = new Company(4, "New World", 1,
                Stream.of(new Employee(1, "God", 30000, "Male", 0, 3))
                        .collect(Collectors.toList()));
    }

    @Test
    public void should_return_all_companies_when_companies_with_no_params() {
        List<Company> expectCompanies = Arrays.asList(company1, company2, company3, company4);
        Mockito.when(companyRepository.findAll()).thenReturn(expectCompanies);

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
        Assert.assertEquals(gson.toJson(expectCompanies), gson.toJson(companies));
    }

    @Test
    public void should_return_page_of_companies_when_companies_with_params() {
        Mockito.when(companyRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(company1)));

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

    @Test
    public void should_return_company_when_get_company_with_id() {
        Mockito.when(companyRepository.findById(any(Integer.class)))
                .thenReturn(java.util.Optional.ofNullable(company3));

        MockMvcResponse response = given()
                .when()
                .get(DEFAULT_PATH_PREFIX + "/companies/3");
        Assert.assertEquals(200, response.getStatusCode());
        String company = response.getBody().asString();
        Assert.assertEquals(gson.toJson(company3), company);
    }

    @Test
    public void should_return_employees_when_get_employees_in_company() {
        Mockito.when(companyRepository.findById(any(Integer.class)))
                .thenReturn(java.util.Optional.ofNullable(company3));

        MockMvcResponse response = given()
                .when()
                .get(DEFAULT_PATH_PREFIX + "/companies/3/employees");
        Assert.assertEquals(200, response.getStatusCode());
        String employee = response.getBody().asString();
        Assert.assertEquals(gson.toJson(company3.getEmployees()), employee);
    }

    @Test
    public void should_return_latest_list_of_companies_when_add_company() {
        Mockito.when(companyRepository.findAll())
                .thenReturn(Arrays.asList(company1, company2, company3, company4));

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(company4)
                .when()
                .post(DEFAULT_PATH_PREFIX + "/companies");

        Assert.assertEquals(200, response.getStatusCode());
        verify(companyRepository, times(1)).save(company4);

        MockMvcResponse responseAfter = given()
                .when()
                .get(DEFAULT_PATH_PREFIX + "/companies");
        List<Company> companies = responseAfter.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(4, companies.size());
        Assert.assertEquals(company4, companies.get(3));
    }

    @Test
    public void should_return_latest_list_of_companies_when_update_company() {
        Mockito.when(companyRepository.getOne(any(Integer.class)))
                .thenReturn(company3);
        Mockito.when(companyRepository.findAll())
                .thenReturn(Arrays.asList(company1, company2, company1));

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(company1)
                .when()
                .put(DEFAULT_PATH_PREFIX + "/companies/3");

        Assert.assertEquals(200, response.getStatusCode());

        MockMvcResponse responseAfter = given()
                .when()
                .get(DEFAULT_PATH_PREFIX + "/companies");
        List<Company> companies = responseAfter.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(company1, companies.get(2));
    }

    @Test
    public void should_return_nothing_when_delete_all_employees_in_company() {
        Company noEmployeeCompany = new Company(3, "New World", 1, new ArrayList<>());
        Mockito.when(companyRepository.getOne(any(Integer.class)))
                .thenReturn(company3);
        Mockito.doNothing().when(companyRepository).deleteById(any(Integer.class));
        Mockito.when(companyRepository.findAll())
                .thenReturn(Arrays.asList(company1, company2, noEmployeeCompany));
    }
}
