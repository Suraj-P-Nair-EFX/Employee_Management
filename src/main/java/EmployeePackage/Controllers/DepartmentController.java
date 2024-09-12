package EmployeePackage.Controllers;

import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {
    APIResponse apiResponse;

    DepartmentService departmentService = new DepartmentService();

    @PostMapping("/department")
    ResponseEntity postCreateDepartment(@RequestBody DepartmentEntity departmentEntity){
        apiResponse =  departmentService.CreateDepartment(departmentEntity);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/department")
    ResponseEntity GetAllDepartment(){
        apiResponse = departmentService.GetAllDepartment();
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/department/{id}")
    ResponseEntity GetDepartment(@PathVariable int id){
        apiResponse = departmentService.GetDepartment(id);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping("/department/{id}")
    ResponseEntity deleteDepartment(@PathVariable int id){
        apiResponse = departmentService.DeleteDepartment(id);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}