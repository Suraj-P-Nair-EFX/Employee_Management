package EmployeePackage.Controllers;

import EmployeePackage.Entities.AddressEntity;
import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testPostEmployee_Success() throws Exception {
        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department);
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employee Created Successfully", employee);

        when(employeeService.createEmployeeService(any(EmployeeEntity.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee Created Successfully"));
    }

    @Test
    public void testGetAllEmployees_Success() throws Exception {
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employees Found Successfully", null);

        when(employeeService.getAllEmployeeService()).thenReturn(apiResponse);

        mockMvc.perform(get("/employee"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employees Found Successfully"));
    }

    @Test
    public void testGetEmployeeById_Success() throws Exception {
        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department);
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employee Found Successfully", employee);

        when(employeeService.getEmployeeById(anyInt())).thenReturn(apiResponse);

        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee Found Successfully"));
    }

    @Test
    public void testDeleteEmployee_Success() throws Exception {
        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department);
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employee Deleted Successfully", employee);

        when(employeeService.deleteEmployee(anyInt())).thenReturn(apiResponse);

        mockMvc.perform(delete("/employee/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee Deleted Successfully"));
    }

    @Test
    public void testUpdateEmployee_Success() throws Exception {
        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department);
        APIResponse<EmployeeEntity> apiResponse = new APIResponse<>(200, "Employee Updated Successfully", employee);

        when(employeeService.updateEmployee(any(EmployeeEntity.class), anyInt())).thenReturn(apiResponse);

        mockMvc.perform(post("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee Updated Successfully"));
    }
}
