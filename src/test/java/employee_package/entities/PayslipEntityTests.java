package employee_package.entities;

import employee_package.extras.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PayslipEntityTests {

    private EmployeeEntity employee;

    @BeforeEach
    void setUp() {
        // Create a mock EmployeeEntity with a SalaryEntity
         employee = new EmployeeEntity(1, "John Doe", 30, new AddressEntity(1,"A","B",1), new DepartmentEntity(1,"HR"), null, new SalaryEntity(1,null,5000,1000,0.0));
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenFieldsAreInvalid() {
        PayslipEntity payslip = new PayslipEntity();
        payslip.setEmployee(employee);
        payslip.setDate(LocalDate.now()); // Set date, but other fields are invalid
        CustomException exception = assertThrows(CustomException.class, payslip::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Input Not Up To Standard", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldNotThrowException_WhenAllFieldsAreValid() {
        PayslipEntity payslip = new PayslipEntity();
        payslip.setEmployee(employee);
        payslip.setDeductions(100);
        payslip.setBonus(200);
        payslip.setPresentDays(20);
        payslip.setTotalDays(30);
        payslip.setDate(LocalDate.now());
        payslip.hasDefault();

    }

    @Test
    void onSave_ShouldCalculateFinalSalary_Correctly() {
        PayslipEntity payslip = new PayslipEntity();
        payslip.setEmployee(employee);
        payslip.setDeductions(100);
        payslip.setBonus(200);
        payslip.setPresentDays(30);
        payslip.setTotalDays(30);
        payslip.setDate(LocalDate.now());
        payslip.onSave();
        double expectedFinalSalary = (((float)5000 * 30 / 30) + 1000 + 200 - 100 - 0);
        assertEquals(expectedFinalSalary, payslip.getFinalSalary());
    }

    @Test
    void equals_ShouldReturnTrue_WhenSameObject() {
            PayslipEntity payslip = new PayslipEntity();
            assertTrue(payslip.equals(payslip)); // Same object should be equal
    }
    @Test
    void equals_ShouldReturnFalse_WhenDifferentClass() {
            PayslipEntity payslip = new PayslipEntity();
            assertFalse(payslip.equals("Not a PayslipEntity")); // Different class should not be equal
    }

    @Test
    void equals_ShouldReturnTrue_WhenSameYearMonthAndEmployee() {
            EmployeeEntity employee1 = new EmployeeEntity(1, "John Doe", 30, null, null, null, new SalaryEntity(1, null, 5000, 1000, 0.0));
            PayslipEntity payslip1 = new PayslipEntity();
            payslip1.setEmployee(employee1);
            payslip1.setDate(LocalDate.of(2023, 10, 1));

            PayslipEntity payslip2 = new PayslipEntity();
            payslip2.setEmployee(employee1);
            payslip2.setDate(LocalDate.of(2023, 10, 15));

            assertTrue(payslip1.equals(payslip2)); // Same year and month, should be equal
    }

    @Test
    void equals_ShouldReturnFalse_WhenDifferentEmployees() {
            EmployeeEntity employee1 = new EmployeeEntity(1, "John Doe", 30, null, null, null, new SalaryEntity(1, null, 5000, 1000, 0.0));
            EmployeeEntity employee2 = new EmployeeEntity(2, "Jane Doe", 25, null, null, null, new SalaryEntity(2, null, 6000, 1200, 0.0));

            PayslipEntity payslip1 = new PayslipEntity();
            payslip1.setEmployee(employee1);
            payslip1.setDate(LocalDate.of(2023, 10, 1));

            PayslipEntity payslip2 = new PayslipEntity();
            payslip2.setEmployee(employee2);
            payslip2.setDate(LocalDate.of(2023, 10, 1));

            assertFalse(payslip1.equals(payslip2)); // Different employees, should not be equal
    }
    @Test
    void hashCode_ShouldReturnSameHashCode_WhenSameYearMonthAndEmployee() {
            EmployeeEntity employee1 = new EmployeeEntity(1, "John Doe", 30, null, null, null, new SalaryEntity(1, null, 5000, 1000, 0.0));
            PayslipEntity payslip1 = new PayslipEntity();
            payslip1.setEmployee(employee1);
            payslip1.setDate(LocalDate.of(2023, 10, 1));

            PayslipEntity payslip2 = new PayslipEntity();
            payslip2.setEmployee(employee1);
            payslip2.setDate(LocalDate.of(2023, 10, 1));

            assertEquals(payslip1.hashCode(), payslip2.hashCode()); // Same year, month, and employee, should have same hash code
    }

    @Test
    void toString_ShouldReturnCorrectStringRepresentation() {
            PayslipEntity payslip = new PayslipEntity();
            payslip.setEmployee(employee);
            payslip.setDeductions(100);
            payslip.setBonus(200);
            payslip.setPresentDays(30);
            payslip.setTotalDays(30);
            payslip.setDate(LocalDate.now());
            payslip.onSave(); // Calculate final salary

            String expectedString = "PayslipEntity {" +
                    "payslipId=0, " + // Assuming payslipId is not set yet
                    "employee=" + employee +
                    ", date=" + payslip.getDate() +
                    ", salaryDetails={" +
                    "basicSalary=" + employee.getSalary().getBasicSalary() +
                    ", allowances=" + employee.getSalary().getAllowances() +
                    ", deductions=100.0, " +
                    "taxPerYear=" + employee.getSalary().getTaxPerYear() +
                    ", bonus=200.0" +
                    "}" +
                    ", attendance={" +
                    "presentDays=30, " +
                    "totalDays=30" +
                    "}" +
                    ", finalSalary=" + payslip.getFinalSalary() +
                    '}';

            assertEquals(expectedString, payslip.toString());
    }
    @Test
    void hasDefault_ShouldThrowCustomException_WhenTotalDaysIsZero() {
        PayslipEntity payslip = new PayslipEntity();
        payslip.setEmployee(employee);
        payslip.setDeductions(100);
        payslip.setBonus(200);
        payslip.setPresentDays(20);
        payslip.setTotalDays(0); // Invalid totalDays
        payslip.setDate(LocalDate.now());
        CustomException exception = assertThrows(CustomException.class, payslip::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Input Not Up To Standard", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenDateIsNull() {
        PayslipEntity payslip = new PayslipEntity();
        payslip.setEmployee(employee);
        payslip.setDeductions(100);
        payslip.setBonus(200);
        payslip.setPresentDays(20);
        payslip.setTotalDays(30);
        payslip.setDate(null); // Invalid date
        CustomException exception = assertThrows(CustomException.class, payslip::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Input Not Up To Standard", exception.getMessage());
    }


    }


