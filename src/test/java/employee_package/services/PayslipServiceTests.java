package employee_package.services;

import employee_package.entities.AddressEntity;
import employee_package.entities.DepartmentEntity;
import employee_package.entities.EmployeeEntity;
import employee_package.entities.PayslipEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.repositories.PayslipRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PayslipServiceTests {

    @InjectMocks private PayslipService payslipService;
    @Mock private PayslipRepo payslipRepo;
    @Mock private EmployeeService employeeService;
    private PayslipEntity payslipEntity;
    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AddressEntity addressEntity = new AddressEntity(1,"123 Main St","City",12345);
        DepartmentEntity departmentEntity = new DepartmentEntity(1,"HR");
        employeeEntity = new EmployeeEntity(1,"John Doe",30,addressEntity,departmentEntity,null,null);
        payslipEntity = new PayslipEntity(1,200,300,20,30,1000,LocalDate.of(2023,10,1),employeeEntity);
    }

    @Test
    void postPayslip_ShouldCreatePayslipSuccessfully() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeId(1)).thenReturn(Collections.emptyList());
        when(payslipRepo.save(any(PayslipEntity.class))).thenReturn(payslipEntity);
        APIResponse<PayslipEntity> response = payslipService.postPayslip(payslipEntity, 1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Payslip Created Successfully", response.getMessage());
        verify(payslipRepo, times(1)).save(any(PayslipEntity.class));
    }

    @Test
    void postPayslip_ShouldThrowException_WhenPayslipAlreadyExists() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeId(1)).thenReturn(List.of(payslipEntity));
        CustomException exception = assertThrows(CustomException.class, () -> payslipService.postPayslip(payslipEntity, 1));
        assertEquals(400, exception.getErrorCode());
        assertEquals("Payslip Already Exists", exception.getMessage());
    }

    @Test
    void getPayslipEncrypted_ShouldReturnPayslipsSuccessfully() {
        when(payslipRepo.findByEmployeeId(1)).thenReturn(List.of(payslipEntity));
        APIResponse<List<PayslipEntity>> response = payslipService.getPayslipEncrypted(1);
        assertEquals(200, response.getErrorCode());
        assertEquals("Payslips Retrieved Successfully", response.getMessage());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getPayslipEncrypted_ShouldReturnEmptyList_WhenNoPayslipsExist() {
        when(payslipRepo.findByEmployeeId(1)).thenReturn(Collections.emptyList());
        APIResponse<List<PayslipEntity>> response = payslipService.getPayslipEncrypted(1);
        assertEquals(200.1, response.getErrorCode());
        assertEquals("Payslip Doesn't Exist", response.getMessage());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getPayslipDecrypted_ShouldReturnPayslipSuccessfully() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeAndDate(any(EmployeeEntity.class), any(LocalDate.class))).thenReturn(payslipEntity);
        APIResponse<PayslipEntity> response = payslipService.getPayslipDecrypted(1, LocalDate.of(2023, 10, 1));
        assertEquals(200, response.getErrorCode());
        assertEquals("Payslip Retrieved Successfully", response.getMessage());
    }

    @Test
    void getPayslipDecrypted_ShouldReturnNotFound_WhenPayslipDoesNotExist() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeAndDate(any(EmployeeEntity.class), any(LocalDate.class))).thenReturn(null);
        APIResponse<PayslipEntity> response = payslipService.getPayslipDecrypted(1, LocalDate.of(2023, 10, 1));
        assertEquals(200.1, response.getErrorCode());
        assertEquals("Payslip Doesn't Exist", response.getMessage());
    }

    @Test
    void deletePayslip_ShouldDeletePayslipSuccessfully() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeAndDate(any(EmployeeEntity.class), any(LocalDate.class))).thenReturn(payslipEntity);
        APIResponse<PayslipEntity> response = payslipService.deletePayslip(1, LocalDate.of(2023, 10, 1));
        assertEquals(200, response.getErrorCode());
        assertEquals("Payslip deleted Successfully", response.getMessage());
        verify(payslipRepo, times(1)).delete(payslipEntity);
    }

    @Test
    void deletePayslip_ShouldThrowException_WhenPayslipDoesNotExist() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeAndDate(any(EmployeeEntity.class), any(LocalDate.class))).thenReturn(null);
        CustomException exception = assertThrows(CustomException.class, () -> payslipService.deletePayslip(1, LocalDate.of(2023, 10, 1)));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Payslip Doesn't Exist", exception.getMessage());
    }

    @Test
    void findPayslipBetweenMonths_ShouldReturnPayslipsSuccessfully() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeAndDateBetween(any(EmployeeEntity.class), any(LocalDate.class), any(LocalDate.class), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(payslipEntity)));
        APIResponse<List<PayslipEntity>> response = payslipService.findPayslipBetweenMonths(1, LocalDate.of(2023, 9, 1), LocalDate.of(2023, 10, 31), 0, 10);
        assertEquals(200, response.getErrorCode());
        assertEquals("Payslips found successfully", response.getMessage());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void findPayslipBetweenMonths_ShouldThrowException_WhenNoPayslipsFound() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findByEmployeeAndDateBetween(any(EmployeeEntity.class), any(LocalDate.class), any(LocalDate.class), any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        CustomException exception = assertThrows(CustomException.class, () -> payslipService.findPayslipBetweenMonths(1, LocalDate.of(2023, 9, 1), LocalDate.of(2023, 10, 31), 0, 10));
        assertEquals(404, exception.getErrorCode());
        assertEquals("Payslip Doesn't Exist", exception.getMessage());
    }

    @Test
    void checkDate_ShouldThrowException_WhenDateIsInFuture() {
        CustomException exception = assertThrows(CustomException.class, () -> payslipService.checkDate(LocalDate.now().plusDays(1)));
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Invalid Date", exception.getMessage());
    }

    @Test
    void decryptPayslip_ShouldDecryptEmployeeSuccessfully() {
        EmployeeEntity decryptedEmployee = new EmployeeEntity(employeeEntity); // Create a decrypted version of the employee
        when(employeeService.decryptEmployee(any(EmployeeEntity.class))).thenReturn(decryptedEmployee);
        PayslipEntity decryptedPayslip = payslipService.decryptPayslip(payslipEntity);
        assertEquals(decryptedEmployee, decryptedPayslip.getEmployee()); // Check if the employee is decrypted correctly
    }

    @Test
    void getPayslipDecrypted_ShouldReturnPayslipsSuccessfully() {
        when(employeeService.getEmployeeByIdEncrypted(1)).thenReturn(new APIResponse<>(200, "Employee found", employeeEntity));
        when(payslipRepo.findAllByEmployeeId(anyInt(), any(Pageable.class))).thenReturn(new PageImpl<>(List.of(payslipEntity)));

        APIResponse<List<PayslipEntity>> response = payslipService.getPayslipDecrypted(1, 0, 10);
        assertEquals(200, response.getErrorCode());
        assertEquals("Payslips Retrieved Successfully", response.getMessage());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void checkDate_ShouldNotThrowException_WhenDateIsValid() {
        // This date is in the past
        LocalDate validDate = LocalDate.now().minusDays(1);
        // This should not throw an exception
        payslipService.checkDate(validDate);
    }


}
