package employee_package.controllers;

import employee_package.entities.*;
import employee_package.extras.APIResponse;
import employee_package.services.PayslipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PayslipControllerTests {

    @InjectMocks private PayslipController payslipController;
    @Mock private PayslipService payslipService;
    private PayslipEntity payslipEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AddressEntity addressEntity = new AddressEntity(1,"123 Main St","City",12345);

        DepartmentEntity departmentEntity = new DepartmentEntity(1,"HR");

        EmployeeEntity employeeEntity = new EmployeeEntity(1,"John Doe",30,addressEntity,departmentEntity,null,null);

        payslipEntity = new PayslipEntity(1,200,300,20,30,1000,LocalDate.of(2023,10,1),employeeEntity);
    }

    @Test
    void postPayslip_ShouldReturnCreatedResponse() {
        when(payslipService.postPayslip(any(PayslipEntity.class), anyInt()))
                .thenReturn(new APIResponse<>(200, "Payslip created", payslipEntity));

        ResponseEntity<APIResponse<PayslipEntity>> response = payslipController.postPayslip(payslipEntity, 1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Payslip created", Objects.requireNonNull(response.getBody()).getMessage());
        verify(payslipService, times(1)).postPayslip(any(PayslipEntity.class), eq(1));
    }

    @Test
    void getPayslip_ShouldReturnOkResponse() {
        when(payslipService.getPayslipDecrypted(anyInt(), anyInt(), anyInt()))
                .thenReturn(new APIResponse<>(200, "Payslips retrieved", Collections.singletonList(payslipEntity)));

        ResponseEntity<APIResponse<List<PayslipEntity>>> response = payslipController.getPayslip(1, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getBody().size());
        verify(payslipService, times(1)).getPayslipDecrypted(eq(1), anyInt(), anyInt());
    }

    @Test
    void getSinglePayslip_ShouldReturnOkResponse() {
        LocalDate date = LocalDate.now();
        when(payslipService.getPayslipDecrypted(anyInt(), any(LocalDate.class)))
                .thenReturn(new APIResponse<>(200, "Payslip retrieved", payslipEntity));

        ResponseEntity<APIResponse<PayslipEntity>> response = payslipController.getPayslip(1, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payslip retrieved", Objects.requireNonNull(response.getBody()).getMessage());
        verify(payslipService, times(1)).getPayslipDecrypted(eq(1), any(LocalDate.class));
    }

    @Test
    void deletePayslip_ShouldReturnOkResponse() {
        LocalDate date = LocalDate.now();
        when(payslipService.deletePayslip(anyInt(), any(LocalDate.class)))
                .thenReturn(new APIResponse<>(200, "Payslip deleted", payslipEntity));

        ResponseEntity<APIResponse<PayslipEntity>> response = payslipController.deletePayslip(1, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payslip deleted", Objects.requireNonNull(response.getBody()).getMessage());
        verify(payslipService, times(1)).deletePayslip(eq(1), any(LocalDate.class));
    }

    @Test
    void getRangePayslip_ShouldReturnOkResponse() {
        LocalDate from = LocalDate.now().minusMonths(1);
        LocalDate to = LocalDate.now();
        when(payslipService.findPayslipBetweenMonths(anyInt(), any(LocalDate.class), any(LocalDate.class), anyInt(), anyInt()))
                .thenReturn(new APIResponse<>(200, "Payslips retrieved", Collections.singletonList(payslipEntity)));

        ResponseEntity<APIResponse<List<PayslipEntity>>> response = payslipController.getRangePayslip(1, from, to, 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getBody().size());
        verify(payslipService, times(1)).findPayslipBetweenMonths(eq(1), any(LocalDate.class), any(LocalDate.class), anyInt(), anyInt());
    }
}
