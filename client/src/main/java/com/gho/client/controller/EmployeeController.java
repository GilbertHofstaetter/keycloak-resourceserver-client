package com.gho.client.controller;

import com.gho.client.clientApi.api.EmployeeControllerApi;
import com.gho.client.clientApi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeControllerApi employeeControllerApi;

    @GetMapping("/list")
    public List<Employee> list() {
        List<Employee> employees = employeeControllerApi.list();
        return employees;
    }
}
