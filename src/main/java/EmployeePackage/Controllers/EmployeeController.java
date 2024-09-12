package EmployeePackage.Controllers;

import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    APIResponse apiResponse;

    @Autowired
    EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/employee")
    ResponseEntity postEmployee(@RequestBody EmployeeEntity employeeEntity) {
        logger.info("Received request to create a new employee");
        apiResponse = employeeService.createEmployeeService(employeeEntity);
        if (apiResponse.getErrorCode() == 200) {
            logger.info("Employee created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else {
            logger.error("Error creating employee: {}", apiResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/employee")
    ResponseEntity getEmployee() {
        logger.info("Received request to get all employees");
        apiResponse = employeeService.getAllEmployeeService();
        if (apiResponse.getErrorCode() == 200) {
            logger.info("Employees retrieved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else {
            logger.error("Error retrieving employees: {}", apiResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @GetMapping("/employee/{id}")
    ResponseEntity getEmployeeById(@PathVariable int id) {
        logger.info("Received request to get employee by ID: {}", id);
        apiResponse = employeeService.getEmployeeById(id);
        if (apiResponse.getErrorCode() == 200) {
            logger.info("Employee retrieved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else {
            logger.error("Error retrieving employee: {}", apiResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @DeleteMapping("/employee/{id}")
    ResponseEntity deleteEmployee(@PathVariable int id) {
        logger.info("Received request to delete employee with ID: {}", id);
        apiResponse = employeeService.deleteEmployee(id);
        if (apiResponse.getErrorCode() == 200) {
            logger.info("Employee deleted successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else {
            logger.error("Error deleting employee: {}", apiResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @PostMapping("/employee/{id}")
    ResponseEntity updateEmployee(@RequestBody EmployeeEntity employeeEntity, @PathVariable int id) {
        logger.info("Received request to update employee with ID: {}", id);
        apiResponse = employeeService.updateEmployee(employeeEntity, id);
        if (apiResponse.getErrorCode() == 200) {
            logger.info("Employee updated successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else {
            logger.error("Error updating employee: {}", apiResponse.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
}
