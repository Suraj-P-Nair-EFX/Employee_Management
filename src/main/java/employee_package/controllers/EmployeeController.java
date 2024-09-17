package employee_package.controllers;

import employee_package.entities.EmployeeEntity;
import employee_package.extras.APIResponse;
import employee_package.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employee") // CREATE EMPLOYEE
    ResponseEntity<APIResponse<EmployeeEntity>> postEmployee(@RequestBody EmployeeEntity employeeEntity) {
        logger.info("Received request to create a new employee");
        APIResponse<EmployeeEntity> apiResponse = employeeService.createEmployeeService(employeeEntity);
        logger.info("Employee created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/employee") // GET ALL EMPLOYEES
    ResponseEntity<APIResponse<List<EmployeeEntity>>> getEmployee() {
        logger.info("Received request to get all employees");
        APIResponse<List<EmployeeEntity>> apiResponse = employeeService.getAllEmployeeService();
        logger.info("Employees retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/employee/{id}") // GET SINGLE EMPLOYEE
    ResponseEntity<APIResponse<EmployeeEntity>> getEmployeeById(@PathVariable int id) {
        logger.info("Received request to get employee by ID: {}", id);
        APIResponse<EmployeeEntity> apiResponse = employeeService.getEmployeeByIdDecrypted(id);
        logger.info("Employee retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/employee/{id}") // DELETE EMPLOYEE
    ResponseEntity<APIResponse<EmployeeEntity>> deleteEmployee(@PathVariable int id) {
        logger.info("Received request to delete employee with ID: {}", id);
        APIResponse<EmployeeEntity> apiResponse = employeeService.deleteEmployee(id);
        logger.info("Employee deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/employee/{id}") // UPDATE EMPLOYEE
    ResponseEntity<APIResponse<EmployeeEntity>> updateEmployee(@RequestBody EmployeeEntity employeeEntity, @PathVariable int id) {
        logger.info("Received request to update employee with ID: {}", id);
        APIResponse<EmployeeEntity> apiResponse = employeeService.updateEmployee(employeeEntity, id);
        logger.info("Employee updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
