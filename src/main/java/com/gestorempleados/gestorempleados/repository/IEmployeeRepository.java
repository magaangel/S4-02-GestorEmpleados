package com.gestorempleados.gestorempleados.repository;


import com.gestorempleados.gestorempleados.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

}
