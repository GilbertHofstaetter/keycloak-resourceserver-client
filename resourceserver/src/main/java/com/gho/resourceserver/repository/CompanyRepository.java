package com.gho.resourceserver.repository;

import com.gho.resourceserver.obj.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
