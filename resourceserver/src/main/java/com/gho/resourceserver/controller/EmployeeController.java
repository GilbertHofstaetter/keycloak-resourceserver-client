package com.gho.resourceserver.controller;

import com.gho.resourceserver.obj.Employee;
import com.gho.resourceserver.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/list")
    public List<Employee> list() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }
}
