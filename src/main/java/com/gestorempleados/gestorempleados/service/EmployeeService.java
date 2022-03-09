package com.gestorempleados.gestorempleados.service;

import com.gestorempleados.gestorempleados.model.Employee;
import com.gestorempleados.gestorempleados.repository.EmployeeRopository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRopository employeeRopository;

    @Override
    public Employee create(Employee employee) {
        return employeeRopository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRopository.update(employee);
    }

    @Override
    public Employee updatePicture(Integer id, MultipartFile file){ // <-------------
        return employeeRopository.updatePicture(id, file);
    }

    @Override
    public Employee findById(Integer id) {
        return employeeRopository.getEmployeeById(id);
    }

    @Override
    public List<Employee> findByPosition(String position) {
        return employeeRopository.getEmployeeByPosition(position);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRopository.getAllEmployee();
    }

    @Override
    public Employee delete(Integer id) {
        return employeeRopository.deleteEmployee(id);

    }
}
