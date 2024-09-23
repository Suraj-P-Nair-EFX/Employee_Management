package employee_package.controllers;

import employee_package.entities.EmployeeEntity;
import employee_package.extras.APIResponse;
import employee_package.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeControllerTests {

    @InjectMocks private EmployeeController employeeController;
    @Mock private EmployeeService employeeService;
    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeEntity = new EmployeeEntity();
    }

    @Test
    void postEmployee_ShouldCreateEmployee() {
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(201, "Employee created", employeeEntity);
        when(employeeService.createEmployeeService(any(EmployeeEntity.class))).thenReturn(apiResponse);

        ResponseEntity<APIResponse<EmployeeEntity>> response = employeeController.postEmployee(employeeEntity);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(employeeService, times(1)).createEmployeeService(employeeEntity);
    }

    @Test
    void getEmployee_ShouldReturnAllEmployees() {
        APIResponse<List<EmployeeEntity>> apiResponse = new APIResponse<>(200, "Employees retrieved", Collections.singletonList(employeeEntity));
        when(employeeService.getAllEmployeeService(0, 10)).thenReturn(apiResponse);

        ResponseEntity<APIResponse<List<EmployeeEntity>>> response = employeeController.getEmployee(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(employeeService, times(1)).getAllEmployeeService(0, 10);
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employee retrieved", employeeEntity);
        when(employeeService.getEmployeeByIdDecrypted(1)).thenReturn(apiResponse);

        ResponseEntity<APIResponse<EmployeeEntity>> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(employeeService, times(1)).getEmployeeByIdDecrypted(1);
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee() {
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employee deleted", employeeEntity);
        when(employeeService.deleteEmployee(1)).thenReturn(apiResponse);

        ResponseEntity<APIResponse<EmployeeEntity>> response = employeeController.deleteEmployee(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(employeeService, times(1)).deleteEmployee(1);
    }

    @Test
    void updateEmployee_ShouldUpdateEmployee() {
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employee updated", employeeEntity);
        when(employeeService.updateEmployee(any(EmployeeEntity.class), eq(1))).thenReturn(apiResponse);

        ResponseEntity<APIResponse<EmployeeEntity>> response = employeeController.updateEmployee(employeeEntity, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(employeeService, times(1)).updateEmployee(employeeEntity, 1);
    }
}
