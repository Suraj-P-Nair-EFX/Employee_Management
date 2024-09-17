//package employee_package.services;
//
//
//import employee_package.entities.AddressEntity;
//import employee_package.entities.DepartmentEntity;
//import employee_package.entities.EmployeeEntity;
//import employee_package.extras.APIResponse;
//import employee_package.repositories.EmployeeRepo;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//public class EmployeeServiceTests {
//
//    @Mock
//    private EmployeeRepo employeeRepo;
//
//    @Mock
//    private DepartmentService departmentService;
//
//    @InjectMocks
//    private EmployeeService employeeService;
//
//    public EmployeeServiceTests() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateEmployee_Success() {
//        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
//        DepartmentEntity department = new DepartmentEntity(1, "HR");
//        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department,null);
//
//        when(departmentService.getDepartment(department.getId())).thenReturn(new APIResponse<>(200, "Department Found Successfully", department));
//        when(employeeRepo.save(employee)).thenReturn(employee);
//        when(employeeRepo.existsById(employee.getId())).thenReturn(false);
//
//        APIResponse response = employeeService.createEmployeeService(employee);
//
//        assertEquals(200, response.getErrorCode());
//        assertEquals("Employee Created Successfully", response.getMessage());
//    }

//    @Test
//    public void testCreateEmployee_NoDepartment_BadRequest() {
//        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
//        DepartmentEntity department = new DepartmentEntity(1, "HR");
//        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department,null);
//
//        when(departmentService.GetDepartment(department.getId())).thenReturn(new APIResponse<>(200.1,"Department Doesn't Exist", null));
//        when(employeeRepo.save(employee)).thenReturn(employee);
//        when(employeeRepo.existsById(employee.getId())).thenReturn(false);
//
//        APIResponse response = employeeService.createEmployeeService(employee);
//
//        assertEquals(200.1, response.getErrorCode());
//        assertEquals("Department Doesn't Exist", response.getMessage());
//    }
//
//    @Test
//    public void testGetAllEmployeeService_Success() {
//        when(employeeRepo.count()).thenReturn(1L);
//        when(employeeRepo.findAll()).thenReturn(List.of(new EmployeeEntity()));
//
//        APIResponse response = employeeService.getAllEmployeeService();
//
//        assertEquals(200, response.getErrorCode());
//        assertEquals("Employees Found Successfully", response.getMessage());
//    }
//
//    @Test
//    public void testGetEmployeeById_Success() {
//        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
//        DepartmentEntity department = new DepartmentEntity(1, "HR");
//        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department,null);
//
//        when(employeeRepo.existsById(employee.getId())).thenReturn(true);
//        when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
//
//        APIResponse response = employeeService.getEmployeeById(employee.getId());
//
//        assertEquals(200, response.getErrorCode());
//        assertEquals("Employee Found Successfully", response.getMessage());
//    }
//
//    @Test
//    public void testDeleteEmployee_Success() {
//        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
//        DepartmentEntity department = new DepartmentEntity(1, "HR");
//        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department,null);
//
//        when(employeeRepo.existsById(employee.getId())).thenReturn(true);
//        when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
//
//        APIResponse response = employeeService.deleteEmployee(employee.getId());
//
//        assertEquals(200, response.getErrorCode());
//        assertEquals("Employee Deleted Successfully", response.getMessage());
//    }
//
//    @Test
//    public void testUpdateEmployee_Success() {
//        AddressEntity address = new AddressEntity("123 Main St", "City", 12345);
//        DepartmentEntity department = new DepartmentEntity(1, "HR");
//        EmployeeEntity existingEmployee = new EmployeeEntity(1, "John Doe", 30, address, department,null);
//        EmployeeEntity updatedEmployee = new EmployeeEntity(1, "John Smith", 35, address, department,null);
//
//        when(employeeRepo.existsById(existingEmployee.getId())).thenReturn(true);
//        when(employeeRepo.findById(existingEmployee.getId())).thenReturn(Optional.of(existingEmployee));
//        when(departmentService.GetDepartment(department.getId())).thenReturn(new APIResponse<>(200, "Department Found", department));
//        when(employeeRepo.save(existingEmployee)).thenReturn(existingEmployee);
//
//        APIResponse response = employeeService.updateEmployee(updatedEmployee, existingEmployee.getId());
//
//        assertEquals(200, response.getErrorCode());
//        assertEquals("Employee Updated Successfully", response.getMessage());
//    }
//}
