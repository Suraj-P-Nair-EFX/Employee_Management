package employee_package.services;

import employee_package.entities.AddressEntity;
import employee_package.entities.DepartmentEntity;
import employee_package.entities.EmployeeEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.extras.ValidationServices;
import employee_package.repositories.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class EmployeeServiceTests {

    @InjectMocks private EmployeeService employeeService;
    @Mock private EmployeeRepo employeeRepo;
    @Mock private DepartmentService departmentService;
    @Mock private EncryptionService encryptionService;
    private EmployeeEntity employeeEntity;

    @Autowired
    ValidationServices validationServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AddressEntity addressEntity = new AddressEntity(1, "123 Main St", "City", 12345);
        DepartmentEntity departmentEntity = new DepartmentEntity(1, "HR");
        employeeEntity =  new EmployeeEntity(1, "John Doe", 25, addressEntity, departmentEntity,null,null);

    }

    @Test
    void createEmployeeService_ShouldCreateEmployee() {
        when(departmentService.getDepartmentDecrypted(anyInt())).thenReturn(new APIResponse<>(200, "Department found", employeeEntity.getDepartment()));
        when(departmentService.getDepartmentEncrypted(anyInt())).thenReturn(new APIResponse<>(200, "Department found", employeeEntity.getDepartment()));
        when(employeeRepo.existsById(anyInt())).thenReturn(false);
        when(employeeRepo.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);
        when(encryptionService.encrypt(anyString())).thenReturn("encryptedName");
        when(encryptionService.decrypt(any())).thenReturn("decryptedName");
        APIResponse<EmployeeEntity> response = employeeService.createEmployeeService(employeeEntity);
        assertEquals(200, response.getErrorCode());
        assertEquals("Employee Created Successfully", response.getMessage());
        assertNotNull(response.getBody());
        verify(employeeRepo, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void getAllEmployeeService_ShouldReturnEmployees() {
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(employeeEntity)));

        APIResponse<List<EmployeeEntity>> response = employeeService.getAllEmployeeService(0, 10);

        assertEquals(200, response.getErrorCode());
        assertEquals("Page 0 Found Successfully", response.getMessage());
        assertFalse(response.getBody().isEmpty());

        verify(employeeRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getEmployeeByIdDecrypted_ShouldReturnEmployee() {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employeeEntity));
        APIResponse<EmployeeEntity> response = employeeService.getEmployeeByIdDecrypted(1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Employee Found Successfully", response.getMessage());
        assertNotNull(response.getBody());
        verify(employeeRepo, times(1)).findById(1);
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee() {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employeeEntity));

        APIResponse<EmployeeEntity> response = employeeService.deleteEmployee(1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Employee Deleted Successfully", response.getMessage());
        verify(employeeRepo, times(1)).delete(employeeEntity);
    }

    @Test
    void updateEmployee_ShouldUpdateEmployee() {
        when(employeeRepo.findById(1)).thenReturn(Optional.of(employeeEntity));
        when(employeeRepo.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);
        when(departmentService.getDepartmentDecrypted(anyInt())).thenReturn(new APIResponse<>(200, "Department found", employeeEntity.getDepartment()));
        when(encryptionService.encrypt(anyString())).thenReturn("encryptedName");
        when(encryptionService.decrypt(any())).thenReturn("decryptedName");
        when(departmentService.getDepartmentEncrypted(anyInt())).thenReturn(new APIResponse<>(200, "Department found", employeeEntity.getDepartment()));

        APIResponse<EmployeeEntity> response = employeeService.updateEmployee(employeeEntity, 1);

        assertEquals(200, response.getErrorCode());
        assertEquals("Employee Updated Successfully", response.getMessage());
        verify(employeeRepo, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void createEmployeeService_ShouldThrowException_WhenEmployeeExists() {
        when(employeeRepo.existsById(anyInt())).thenReturn(true);
        when(departmentService.getDepartmentDecrypted(anyInt())).thenReturn(new APIResponse<>(200, "Department found", employeeEntity.getDepartment()));
        CustomException exception = assertThrows(CustomException.class, () -> employeeService.createEmployeeService(employeeEntity));
        //assertEquals(400, exception.getErrorCode());
        assertEquals("Employee Already Exists", exception.getMessage());
    }

    @Test
    void getEmployeeByIdDecrypted_ShouldThrowException_WhenEmployeeNotFound() {
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> employeeService.getEmployeeByIdDecrypted(1));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Employee Not Found", exception.getMessage());
    }

    @Test
    void deleteEmployee_ShouldThrowException_WhenEmployeeNotFound() {
        when(employeeRepo.findById(1)).thenReturn(Optional.empty());
        CustomException exception = assertThrows(CustomException.class, () -> employeeService.deleteEmployee(1));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Employee Not Found", exception.getMessage());
    }

    @Test
    void getAllEmployeeService_ShouldThrowException_WhenPageIsOutOfBounds() {
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(employeeEntity)));

        CustomException exception = assertThrows(CustomException.class, () -> employeeService.getAllEmployeeService(1, 10));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Not Found", exception.getMessage());
    }

    @Test
    void getAllEmployeeService_ShouldThrowException_WhenNoEmployeesFound() {
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

        CustomException exception = assertThrows(CustomException.class, () -> employeeService.getAllEmployeeService(0, 10));
        assertEquals(404, exception.getErrorCode());
        assertEquals("No Employees Found", exception.getMessage());
    }

    @Test
    void getAllEmployeeService_ShouldReturnEmployees_WhenValidPageAndSize() {
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(employeeEntity)));

        APIResponse<List<EmployeeEntity>> response = employeeService.getAllEmployeeService(0, 10);

        assertEquals(200, response.getErrorCode());
        assertEquals("Page 0 Found Successfully", response.getMessage());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(employeeEntity, response.getBody().get(0));

        verify(employeeRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getAllEmployeeService_ShouldThrowException_WhenPageSizeIsZero() {
        CustomException exception = assertThrows(CustomException.class, () -> employeeService.getAllEmployeeService(0, 0));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Page size must be greater than zero", exception.getMessage());
    }

    @Test
    void getAllEmployeeService_ShouldThrowException_WhenPageIsNegative() {
        CustomException exception = assertThrows(CustomException.class, () -> employeeService.getAllEmployeeService(-1, 10));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Page number must be non-negative", exception.getMessage());
    }

    @Test
    void getAllEmployeeService_ShouldThrowException_WhenPageIsGreaterThanTotalPages() {
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(employeeEntity)));

        CustomException exception = assertThrows(CustomException.class, () -> employeeService.getAllEmployeeService(2, 1));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Not Found", exception.getMessage());
    }

    @Test
    void getAllEmployeeService_ShouldHandleLargePageSize() {
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(employeeEntity)));

        APIResponse<List<EmployeeEntity>> response = employeeService.getAllEmployeeService(0, 1000);

        assertEquals(200, response.getErrorCode());
        assertEquals("Page 0 Found Successfully", response.getMessage());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(employeeEntity, response.getBody().get(0));

        verify(employeeRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getAllEmployeeService_ShouldDecryptEmployees() {
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(employeeEntity)));
        when(encryptionService.decrypt(any())).thenReturn("decryptedName");

        APIResponse<List<EmployeeEntity>> response = employeeService.getAllEmployeeService(0, 10);

        assertEquals(200, response.getErrorCode());
        assertEquals("Page 0 Found Successfully", response.getMessage());
        assertFalse(response.getBody().isEmpty());
        assertEquals("decryptedName", response.getBody().get(0).getName()); // Assuming name is decrypted

        verify(employeeRepo, times(1)).findAll(any(Pageable.class));
    }


}
