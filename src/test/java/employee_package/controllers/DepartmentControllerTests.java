package employee_package.controllers;
import employee_package.entities.DepartmentEntity;
import employee_package.extras.APIResponse;
import employee_package.services.DepartmentService;
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

class DepartmentControllerTests {

    @InjectMocks private DepartmentController departmentController;
    @Mock private DepartmentService departmentService;
    private DepartmentEntity departmentEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentEntity = new DepartmentEntity();
        departmentEntity.setId(1);
        departmentEntity.setName("HR");
    }

    @Test
    void postCreateDepartment_ShouldCreateDepartment() {
        APIResponse<DepartmentEntity> apiResponse = new APIResponse<>(200, "Department created successfully", departmentEntity);
        when(departmentService.createDepartment(any(DepartmentEntity.class))).thenReturn(apiResponse);
        ResponseEntity<APIResponse<DepartmentEntity>> response = departmentController.postCreateDepartment(departmentEntity);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(departmentService, times(1)).createDepartment(departmentEntity);
    }

    @Test
    void getAllDepartment_ShouldReturnDepartments() {
        APIResponse<List<DepartmentEntity>> apiResponse = new APIResponse<>(200, "Departments retrieved successfully", Collections.singletonList(departmentEntity));
        when(departmentService.getAllDepartment()).thenReturn(apiResponse);
        ResponseEntity<APIResponse<List<DepartmentEntity>>> response = departmentController.getAllDepartment();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(departmentService, times(1)).getAllDepartment();
    }

    @Test
    void getDepartment_ShouldReturnDepartment() {
        APIResponse<DepartmentEntity> apiResponse = new APIResponse<>(200, "Department retrieved successfully", departmentEntity);
        when(departmentService.getDepartmentDecrypted(1)).thenReturn(apiResponse);
        ResponseEntity<APIResponse<DepartmentEntity>> response = departmentController.getDepartment(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(departmentService, times(1)).getDepartmentDecrypted(1);
    }

    @Test
    void deleteDepartment_ShouldDeleteDepartment() {
        APIResponse<DepartmentEntity> apiResponse = new APIResponse<>(200, "Department deleted successfully", departmentEntity);
        when(departmentService.deleteDepartment(1)).thenReturn(apiResponse);
        ResponseEntity<APIResponse<DepartmentEntity>> response = departmentController.deleteDepartment(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(departmentService, times(1)).deleteDepartment(1);
    }

    @Test
    void updateDepartment_ShouldUpdateDepartment() {
        APIResponse<DepartmentEntity> apiResponse = new APIResponse<>(200, "Department updated successfully", departmentEntity);
        when(departmentService.updateDepartment(any(DepartmentEntity.class), eq(1))).thenReturn(apiResponse);
        ResponseEntity<APIResponse<DepartmentEntity>> response = departmentController.updateDepartment(departmentEntity, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(apiResponse, response.getBody());
        verify(departmentService, times(1)).updateDepartment(departmentEntity, 1);
    }
}
