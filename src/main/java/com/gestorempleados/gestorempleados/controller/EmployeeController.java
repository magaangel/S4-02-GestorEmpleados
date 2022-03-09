package com.gestorempleados.gestorempleados.controller;

import com.gestorempleados.gestorempleados.model.Employee;
import com.gestorempleados.gestorempleados.my_exceptions.ResponseNotFoundException;
import com.gestorempleados.gestorempleados.service.EmployeeService;
import com.gestorempleados.gestorempleados.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/v1")
public class EmployeeController {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public MultiValueMap<String, String> defineCustomerHeader(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("IT-Academy-Excercise", "Simple-Service");
        return map;
    }

    @GetMapping("/test")
    public String test(){
        return "Prueba de mi gestor de empleados";
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll(){
        return new ResponseEntity<>(employeeService.findAll(), this.defineCustomerHeader(), HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> insertEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.create(employee), this.defineCustomerHeader(), HttpStatus.CREATED);
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee e = employeeService.update(employee);
        if(e == null){
            return new ResponseEntity<>(this.defineCustomerHeader(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(e, this.defineCustomerHeader(), HttpStatus.OK);
    }

    @PutMapping("/employeesWithPicture/update={id}")
    public ResponseEntity<Employee> updateEmployeePicture(@PathVariable("id") Integer id, @RequestParam MultipartFile file){// <---------
        Employee employee = employeeService.updatePicture(id, file);
        if(employee == null){
            return new ResponseEntity<>(this.defineCustomerHeader(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee, this.defineCustomerHeader(), HttpStatus.OK);
    }

    @GetMapping("/downloadFile/{id}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id, HttpServletRequest request) {
        //--> cargar file como recurso
        FileStorageService fileStorageService = new FileStorageService(employeeService.findById(id));
        Resource resource = fileStorageService.loadFileAsResource(fileStorageService.getFileStorageLocation().toString());
        //--> determinar el tipo del contenido del file
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            LOG.info("Could not determine file type.");
        }
        //--> Vuelve al tipo de contenido predeterminado si no se pudo determinar el tipo
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("employees/id={id}")
    public ResponseEntity<Employee> findById(@PathVariable(name = "id") Integer id){
        Employee employee = employeeService.findById(id);
        if(employee == null){
        //    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new ResponseNotFoundException("employee id not found");//--> status not found lanzado con la exception creada
        }
        return new ResponseEntity<>(employee, this.defineCustomerHeader(), HttpStatus.OK);
    }

    @GetMapping("employees/position={position}")
    public ResponseEntity<List<Employee>> findByPosition(@PathVariable(name = "position") String position){
        List<Employee> emList = employeeService.findByPosition(position);
        if(emList == null){
            return new ResponseEntity<>(this.defineCustomerHeader(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(emList, this.defineCustomerHeader(), HttpStatus.OK);
    }

    //otra forma de lanzar un status not found es por medio de la creacion de un exception
    @DeleteMapping("/employees/delete/id={id}")
    public ResponseEntity<Employee> delete(@PathVariable(name = "id") Integer id) throws Exception {
        Employee employee = employeeService.delete(id);
        if(employee == null){
            throw new ResponseNotFoundException("employee id not found");//--> exception creada, mensaje que lanza
        }
        return new ResponseEntity<>(employee, this.defineCustomerHeader(), HttpStatus.OK);
    }

}
