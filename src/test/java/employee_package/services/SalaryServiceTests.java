package employee_package.services;

import employee_package.entities.AddressEntity;
import employee_package.entities.DepartmentEntity;
import employee_package.entities.EmployeeEntity;
import employee_package.entities.SalaryEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.repositories.SalaryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class SalaryServiceTests {
    @InjectMocks private SalaryService salaryService;
    @Mock private SalaryRepo salaryRepo;
    @Mock private EmployeeService employeeService;
    private SalaryEntity salaryEntity;
    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AddressEntity addressEntity = new AddressEntity(1,"123 Main St","City",12345);
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"HR");
        employeeEntity = new EmployeeEntity(1,"John Doe",30,addressEntity,departmentEntity,null,null);
        salaryEntity = new SalaryEntity(1, employeeEntity,50000,0,0.0);
    }

    @Test
    void createSalary_ShouldReturnSuccessResponse() {
        when(employeeService.getEmployeeByIdEncrypted(anyInt())).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(salaryRepo.existsById(anyInt())).thenReturn(false);
        when(salaryRepo.save(any(SalaryEntity.class))).thenReturn(salaryEntity);
        APIResponse<SalaryEntity> response = salaryService.createSalary(salaryEntity, 1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Added salary successfully", response.getMessage());
        assertEquals(salaryEntity, response.getBody());
        verify(salaryRepo, times(1)).save(any(SalaryEntity.class));
    }

    @Test
    void createSalary_ShouldThrowException_WhenSalaryAlreadyExists() {
        when(salaryRepo.existsById(anyInt())).thenReturn(true);
        when(employeeService.getEmployeeByIdEncrypted(anyInt())).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        CustomException exception = assertThrows(CustomException.class, () -> salaryService.createSalary(salaryEntity, 1));
        assertEquals(400, exception.getErrorCode());
        assertEquals("Salary already exists for this employee", exception.getMessage());
    }

    @Test
    void getSalaryDecrypt_ShouldReturnSuccessResponse() {
        when(salaryRepo.findById(anyInt())).thenReturn(Optional.of(salaryEntity));
        APIResponse<SalaryEntity> response = salaryService.getSalaryDecrypt(1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Salary Retrieved Successfully", response.getMessage());
        assertEquals(salaryEntity, response.getBody());
    }

    @Test
    void getSalaryDecrypt_ShouldThrowException_WhenSalaryNotFound() {
        when(salaryRepo.findById(anyInt())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> {
            salaryService.getSalaryDecrypt(1);
        });
        assertEquals(404, exception.getErrorCode());
        assertEquals("Salary Not Found", exception.getMessage());
    }

    @Test
    void getALlSalaries_ShouldReturnSuccessResponse() {
        when(salaryRepo.findAll()).thenReturn(Collections.singletonList(salaryEntity));
        APIResponse<List<SalaryEntity>> response = salaryService.getALlSalaries();
        assertEquals(200, response.getErrorCode());
        assertEquals("Retrieved Salaries Successfully", response.getMessage());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getALlSalaries_ShouldThrowException_WhenNoSalariesFound() {
        when(salaryRepo.findAll()).thenReturn(Collections.emptyList());
        CustomException exception = assertThrows(CustomException.class, () -> {
            salaryService.getALlSalaries();
        });
        assertEquals(404, exception.getErrorCode());
        assertEquals("Salary Not Found", exception.getMessage());
    }

    @Test
    void deleteSalary_ShouldReturnSuccessResponse() {
        when(salaryRepo.findById(anyInt())).thenReturn(Optional.of(salaryEntity));
        APIResponse<SalaryEntity> response = salaryService.deleteSalary(1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Salary Deleted Successfully", response.getMessage());
        verify(salaryRepo, times(1)).delete(salaryEntity);
    }

    @Test
    void deleteSalary_ShouldThrowException_WhenSalaryNotFound() {
        when(salaryRepo.findById(anyInt())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> {
            salaryService.deleteSalary(1);
        });
        assertEquals(404, exception.getErrorCode());
        assertEquals("Salary Not Found", exception.getMessage());
    }

    @Test
    void updateSalary_ShouldReturnSuccessResponse() {
        SalaryEntity updatedSalary = new SalaryEntity(salaryEntity);
        when(salaryRepo.findById(anyInt())).thenReturn(Optional.of(salaryEntity));
        APIResponse<SalaryEntity> response = salaryService.updateSalary(updatedSalary, 1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Salary Updated Successfully", response.getMessage());
        assertEquals(salaryEntity, response.getBody());
        verify(salaryRepo, times(1)).save(salaryEntity);
    }

    @Test
    void updateSalary_ShouldThrowException_WhenSalaryNotFound() {
        when(salaryRepo.findById(anyInt())).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> {
            salaryService.updateSalary(salaryEntity, 1);
        });
        assertEquals(404, exception.getErrorCode());
        assertEquals("Salary Not Found", exception.getMessage());
    }
}
