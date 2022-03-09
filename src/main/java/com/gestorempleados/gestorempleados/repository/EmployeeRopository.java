package com.gestorempleados.gestorempleados.repository;

import com.gestorempleados.gestorempleados.model.Employee;
import com.gestorempleados.gestorempleados.service.FileStorageService;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRopository {

    //@Autowired //--> se genera un registro con los valores default(null, 0, etc)
    private List<Employee> employeeList = new ArrayList<>();

    public Employee save(Employee employee){
        employeeList.add(employee);
        if(employee.getPosition().equalsIgnoreCase("directivo")){
            employee.setSalary(5500.50);
        }else if(employee.getPosition().equalsIgnoreCase("administrativo")){
            employee.setSalary(3500.50);
        }else if(employee.getPosition().equalsIgnoreCase("obrero")) {
            employee.setSalary(1050.50);
        }
        return employee;
    }

    public Employee update(Employee employee){
        Employee e = this.getEmployeeById(employee.getId());
        if(e != null){
            if(employee.getName() != null) e.setName(employee.getName());
            if(employee.getLastName() != null) e.setLastName(employee.getLastName());
            if(employee.getPosition() != null) e.setPosition(employee.getPosition());
        }
        return e;
    }

    public Employee updatePicture(Integer id, MultipartFile file){// <---------
        Employee e = this.getEmployeeById(id);
        FileStorageService fileStorageService = new FileStorageService(e);
        if(e != null){
            e.setUploadDir(fileStorageService.storeFile(file));
        }
        return e;
    }

    public Employee getEmployeeById(Integer id){
        Employee employee = null;
            for (Employee e : this.employeeList) {
                if (e.getId() == id) {
                    employee = e;
                }
            }
        return employee;
    }

    public List<Employee> getEmployeeByPosition(String position){
        List<Employee> empPositionList = new ArrayList<>();
        for(Employee e : this.employeeList){
            if(e.getPosition().equalsIgnoreCase(position)){
                empPositionList.add(e);
            }
        }
        if(!empPositionList.isEmpty()){
            return empPositionList;
        }
        return null;
    }

    public List<Employee> getAllEmployee(){
        return this.employeeList;
    }

    //segun lo leido es una buena practica que regrese el elemento a eliminar por si se necesita trabajar con este en otro apartado
    public Employee deleteEmployee(Integer id){
        Employee employee = this.getEmployeeById(id);
        if(employee != null){
           employeeList.removeIf(x -> x.getId() == id);
        }
        return employee;
    }

//  Otra manera de hacerlo que regrese true si se ha eliminado el elemento buscado
//   public boolean deleteEmployee(Integer id){
//       Employee employee = this.getEmployeeById(id);
//       if(employee != null){
//           employeeList.removeIf(x -> x.getId() == id);
//           return true;
//       }
//       return false;
//   }

}
