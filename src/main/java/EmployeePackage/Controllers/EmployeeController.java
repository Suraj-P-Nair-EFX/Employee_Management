package EmployeePackage.Controllers;

import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Extras.ValidationServices;
import EmployeePackage.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    APIResponse apiResponse;

    @Autowired
    EmployeeService employeeService;

    ValidationServices stringFilter = new ValidationServices();

    @PostMapping("/employee")
    ResponseEntity postEmployee(@RequestBody EmployeeEntity employeeEntity){
        apiResponse =  employeeService.createEmployeeService(employeeEntity);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/employee")
    ResponseEntity getEmployee(){
        apiResponse = employeeService.getAllEmployeeService();
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/employee/{id}")
    ResponseEntity getEmployeeById(@PathVariable int id){
        apiResponse = employeeService.getEmployeeById(id);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/employee/{id}")
    ResponseEntity deleteEmployee(@PathVariable int id){
        EmployeeEntity employee = employeeService.deleteEmployee(id);
        if(employee == null){
            apiResponse = new APIResponse<>(200.1,"Employees not found",null);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
        }
        apiResponse = new APIResponse<>(200,"Employee Deleted Successfully", employee);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
