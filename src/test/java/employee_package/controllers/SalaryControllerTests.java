package employee_package.controllers;

import employee_package.entities.*;
import employee_package.extras.APIResponse;
import employee_package.services.SalaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class SalaryControllerTests {

    @InjectMocks private SalaryController salaryController;
    @Mock private SalaryService salaryService;
    private SalaryEntity salaryEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AddressEntity addressEntity = new AddressEntity(1,"123 Main St","City",12345);
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"HR");
        EmployeeEntity employeeEntity = new EmployeeEntity(1,"John Doe",30,addressEntity,departmentEntity,null,null);
        salaryEntity = new SalaryEntity(1, employeeEntity,50000,0,0.0);
    }

    @Test
    void createSalary_ShouldReturnOkResponse() {
        when(salaryService.createSalary(any(SalaryEntity.class), anyInt()))
                .thenReturn(new APIResponse<>(200, "Salary created", salaryEntity));
        ResponseEntity<APIResponse<SalaryEntity>> response = salaryController.createSalary(salaryEntity, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Salary created", Objects.requireNonNull(response.getBody()).getMessage());
        verify(salaryService, times(1)).createSalary(any(SalaryEntity.class), eq(1));
    }

    @Test
    void getSalaryOfOneEmployee_ShouldReturnOkResponse() {
        when(salaryService.getSalaryDecrypt(anyInt()))
                .thenReturn(new APIResponse<>(200, "Salary retrieved", salaryEntity));
        ResponseEntity<APIResponse<SalaryEntity>> response = salaryController.getSalaryOfOneEmployee(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Salary retrieved", Objects.requireNonNull(response.getBody()).getMessage());
        verify(salaryService, times(1)).getSalaryDecrypt(1);
    }

    @Test
    void getAllSalaries_ShouldReturnOkResponse() {
        when(salaryService.getALlSalaries())
                .thenReturn(new APIResponse<>(200, "Salaries retrieved", Collections.singletonList(salaryEntity)));
        ResponseEntity<APIResponse<List<SalaryEntity>>> response = salaryController.getAllSalaries();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getBody().size());
        assertEquals("Salaries retrieved", response.getBody().getMessage());
        verify(salaryService, times(1)).getALlSalaries();
    }

    @Test
    void deleteSalary_ShouldReturnOkResponse() {
        when(salaryService.deleteSalary(anyInt()))
                .thenReturn(new APIResponse<>(200, "Salary deleted", salaryEntity));
        ResponseEntity<APIResponse<SalaryEntity>> response = salaryController.deleteSalary(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Salary deleted", Objects.requireNonNull(response.getBody()).getMessage());
        verify(salaryService, times(1)).deleteSalary(1);
    }

    @Test
    void updateSalary_ShouldReturnOkResponse() {
        when(salaryService.updateSalary(any(SalaryEntity.class), anyInt()))
                .thenReturn(new APIResponse<>(200, "Salary updated", salaryEntity));
        ResponseEntity<APIResponse<SalaryEntity>> response = salaryController.updateSalary(salaryEntity, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Salary updated", Objects.requireNonNull(response.getBody()).getMessage());
        verify(salaryService, times(1)).updateSalary(any(SalaryEntity.class), eq(1));
    }
}
