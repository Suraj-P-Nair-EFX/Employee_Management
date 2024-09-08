package EmployeePackage.Controllers;

import EmployeePackage.Entities.DepartmentEntity;
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

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/department")
    ResponseEntity postCreateDepartment(@RequestBody DepartmentEntity departmentEntity){
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.CreateDepartment(departmentEntity));
    }
}
