package employee_package.controllers;

import employee_package.entities.DepartmentEntity;
import employee_package.extras.APIResponse;
import employee_package.services.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/department") // CREATE DEPARTMENT
    ResponseEntity<APIResponse<DepartmentEntity>> postCreateDepartment(@RequestBody DepartmentEntity departmentEntity) {
        logger.info("Received request to create a new department: {}", departmentEntity);
        APIResponse<DepartmentEntity> apiResponse = departmentService.createDepartment(departmentEntity);
        logger.info("Department created successfully: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/department") // GET ALL DEPARTMENTS
    ResponseEntity<APIResponse<List<DepartmentEntity>>> getAllDepartment() {
        logger.info("Received request to get all departments");
        APIResponse<List<DepartmentEntity>> apiResponse = departmentService.getAllDepartment();
        logger.info("Departments retrieved successfully: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/department/{id}") // GET SINGLE DEPARTMENT
    ResponseEntity<APIResponse<DepartmentEntity>> getDepartment(@PathVariable int id) {
        logger.info("Received request to get department with ID: {}", id);
        APIResponse<DepartmentEntity> apiResponse = departmentService.getDepartmentDecrypted(id);
        logger.info("Department retrieved successfully: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/department/{id}") // DELETE DEPARTMENT
    ResponseEntity<APIResponse<DepartmentEntity>> deleteDepartment(@PathVariable int id) {
        logger.info("Received request to delete department with ID: {}", id);
        APIResponse<DepartmentEntity> apiResponse = departmentService.deleteDepartment(id);
        logger.info("Department deleted successfully: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/department/{id}") // UPDATE DEPARTMENT
    ResponseEntity<APIResponse<DepartmentEntity>> updateDepartment(@RequestBody DepartmentEntity department, @PathVariable int id) {
        logger.info("Received request to update department with ID: {}", id);
        APIResponse<DepartmentEntity> apiResponse = departmentService.updateDepartment(department, id);
        logger.info("Department updated successfully: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
