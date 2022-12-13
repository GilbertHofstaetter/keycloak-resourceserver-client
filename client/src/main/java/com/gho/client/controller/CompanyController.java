package com.gho.client.controller;

import com.gho.client.clientApi.api.CompanyControllerApi;
import com.gho.client.clientApi.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyControllerApi companyControllerApi;

    @GetMapping("/list")
    //@RolesAllowed("ROLE_user")
    //@PreAuthorize("hasAuthority('ROLE_user')")
    @PreAuthorize("hasAnyAuthority('ROLE_user', 'ROLE_admin') AND hasAuthority('SCOPE_read')")
    //Scope read added to client springboot-microservice in keycloak server and set optional
    //read scope is only present in token when requested as scope profile email read ... on token retrieval
    public List<Company> list() {
        List<Company> companies = companyControllerApi.list1();
        return companies;
    }
}
