package com.gho.resourceserver.repository;

import com.gho.resourceserver.obj.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
