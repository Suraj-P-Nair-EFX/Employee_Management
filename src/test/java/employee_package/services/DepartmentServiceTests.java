package employee_package.services;

import employee_package.entities.DepartmentEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.repositories.DepartmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepartmentServiceTests {

    @InjectMocks private DepartmentService departmentService;
    @Mock private DepartmentRepo departmentRepo;
    @Mock private EncryptionService encryptionService;
    private DepartmentEntity department;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        department = new DepartmentEntity();
        department.setId(1);
        department.setName("HR");
    }

    @Test
    void createDepartment_ShouldReturnSuccessResponse_WhenDepartmentIsCreated() {
        when(departmentRepo.existsById(department.getId())).thenReturn(false);

        when(encryptionService.encrypt(department.getName())).thenReturn("encryptedHR");
        when(encryptionService.decrypt(department.getName())).thenReturn("HR");
        when(departmentRepo.save(any(DepartmentEntity.class))).thenReturn(department);

        APIResponse<DepartmentEntity> response = departmentService.createDepartment(department);

        assertEquals(200, response.getErrorCode());
        assertEquals("Department Created Successfully", response.getMessage());
        assertNotNull(response.getBody());
        assertEquals(department.getId(), response.getBody().getId());
        assertEquals(department.getName(), response.getBody().getName());
    }

    @Test
    void createDepartment_ShouldThrowException_WhenDepartmentAlreadyExists() {
        when(departmentRepo.existsById(department.getId())).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> departmentService.createDepartment(department));

        assertEquals(400, exception.getErrorCode());
        assertEquals("Department Already Exists", exception.getMessage());
    }

    @Test
    void getDepartmentDecrypted_ShouldReturnDepartment_WhenExists() {
        when(departmentRepo.findById(department.getId())).thenReturn(Optional.of(department));
        when(encryptionService.decrypt(department.getName())).thenReturn(department.getName());

        APIResponse<DepartmentEntity> response = departmentService.getDepartmentDecrypted(department.getId());

        assertEquals(200, response.getErrorCode());
        assertEquals("Department Found Successfully", response.getMessage());
        assertEquals(department.getId(), response.getBody().getId());
    }

    @Test
    void getDepartmentDecrypted_ShouldThrowException_WhenNotFound() {
        when(departmentRepo.findById(department.getId())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> departmentService.getDepartmentDecrypted(department.getId()));

        assertEquals(404, exception.getErrorCode());
        assertEquals("Department Not Found", exception.getMessage());
    }

    @Test
    void getAllDepartment_ShouldReturnDepartments_WhenExists() {
        when(departmentRepo.findAll()).thenReturn(Collections.singletonList(department));
        when(encryptionService.decrypt(department.getName())).thenReturn(department.getName());

        APIResponse<List<DepartmentEntity>> response = departmentService.getAllDepartment();

        assertEquals(200, response.getErrorCode());
        assertEquals("Departments Found Successfully", response.getMessage());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllDepartment_ShouldThrowException_WhenNoDepartments() {
        when(departmentRepo.findAll()).thenReturn(Collections.emptyList());
        CustomException exception = assertThrows(CustomException.class, () -> departmentService.getAllDepartment());
        assertEquals(404.0, exception.getErrorCode());
        assertEquals("No Departments To Show", exception.getMessage());
    }

    @Test
    void deleteDepartment_ShouldReturnSuccessResponse_WhenDepartmentIsDeleted() {
        when(departmentRepo.findById(1)).thenReturn(Optional.of(department));

        APIResponse<DepartmentEntity> response = departmentService.deleteDepartment(department.getId());

        assertEquals(200, response.getErrorCode());
        assertEquals("Department Deleted Successfully", response.getMessage());



        verify(departmentRepo, times(1)).findById(1);
        verify(departmentRepo, times(1)).delete(department);

    }

    @Test
    void deleteDepartment_ShouldThrowException_WhenDepartmentNotFound() {
        when(departmentRepo.findById(department.getId())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> departmentService.deleteDepartment(department.getId()));

        assertEquals(404, exception.getErrorCode());
        assertEquals("Department Not Found", exception.getMessage());
    }

    @Test
    void updateDepartment_ShouldReturnSuccessResponse_WhenDepartmentIsUpdated() {
        when(departmentRepo.existsById(department.getId())).thenReturn(true);
        when(encryptionService.encrypt(department.getName())).thenReturn("encryptedHR");
        when(departmentRepo.save(any(DepartmentEntity.class))).thenReturn(department);

        APIResponse<DepartmentEntity> response = departmentService.updateDepartment(department, department.getId());

        assertEquals(200, response.getErrorCode());
        assertEquals("Department updated Successfully", response.getMessage());
        assertEquals(department.getId(), response.getBody().getId());
    }

    @Test
    void updateDepartment_ShouldThrowException_WhenDepartmentDoesNotExist() {
        when(departmentRepo.existsById(department.getId())).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> departmentService.updateDepartment(department, department.getId()));

        assertEquals(404, exception.getErrorCode());
        assertEquals("Department Doesn't Exist", exception.getMessage());
    }
}
