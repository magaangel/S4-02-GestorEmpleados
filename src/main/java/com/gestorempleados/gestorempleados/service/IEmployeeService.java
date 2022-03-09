package com.gestorempleados.gestorempleados.service;

import com.gestorempleados.gestorempleados.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEmployeeService {

    public Employee create(Employee employee);

    public Employee update(Employee employee);

    public Employee updatePicture(Integer id, MultipartFile file);

    public Employee findById(Integer id);

    public List<Employee> findByPosition(String position);

    public List<Employee> findAll();

    Employee delete(Integer id);


}
