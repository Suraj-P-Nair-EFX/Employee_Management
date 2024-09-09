package EmployeePackage.Controllers;

import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Services.DepartmentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DepartmentController {
    APIResponse apiResponse;

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/department")
    ResponseEntity postCreateDepartment(@RequestBody DepartmentEntity departmentEntity){
        apiResponse =  departmentService.CreateDepartment(departmentEntity);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
