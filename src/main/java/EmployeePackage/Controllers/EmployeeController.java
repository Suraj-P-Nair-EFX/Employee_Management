package EmployeePackage.Controllers;

import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class EmployeeController {

    APIResponse apiResponse;
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity postEmployee(@RequestBody EmployeeEntity employeeEntity){
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
        apiResponse = employeeService.deleteEmployee(id);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PostMapping("/employee/{id}")
    public ResponseEntity updateEmployee(@RequestBody EmployeeEntity employeeEntity,@PathVariable int id){
        apiResponse = employeeService.updateEmployee(employeeEntity,id);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
