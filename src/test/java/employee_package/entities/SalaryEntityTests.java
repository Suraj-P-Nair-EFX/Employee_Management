package employee_package.entities;

import employee_package.extras.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaryEntityTests {

    private SalaryEntity salaryEntity;

    @BeforeEach
    void setUp() {
        salaryEntity = new SalaryEntity();
    }

    @Test
    void testHasDefault_ValidInput() {
        salaryEntity.setBasicSalary(30000);
        salaryEntity.setAllowances(5000);
        assertDoesNotThrow(salaryEntity::hasDefault);
    }

    @Test
    void testHasDefault_InvalidBasicSalary() {
        salaryEntity.setBasicSalary(20000);
        salaryEntity.setAllowances(5000);
        CustomException exception = assertThrows(CustomException.class, salaryEntity::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Input Not Up To Standard", exception.getMessage());
    }

    @Test
    void testHasDefault_InvalidAllowances() {
        salaryEntity.setBasicSalary(30000);
        salaryEntity.setAllowances(-1);
        CustomException exception = assertThrows(CustomException.class, salaryEntity::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Input Not Up To Standard", exception.getMessage());
    }

    @Test
    void testOnSave_TaxCalculation() {
        salaryEntity.setBasicSalary(30000);
        salaryEntity.setAllowances(5000);
        salaryEntity.onSave();
        double expectedTax = 3000.0;
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

    @Test
    void testOnSave_TaxCalculation_AboveThreshold() {
        salaryEntity.setBasicSalary(60000);
        salaryEntity.setAllowances(10000);
        salaryEntity.onSave();
        double expectedTax = ((7_20_000-7_00_000) * 0.1) + (0.05 * 4_00_000);
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

    @Test
    void testCopyConstructor() {
        salaryEntity.setId(1);
        salaryEntity.setBasicSalary(30000);
        salaryEntity.setAllowances(5000);
        salaryEntity.onSave(); // Calculate tax

        SalaryEntity copiedEntity = new SalaryEntity(salaryEntity);

        assertEquals(salaryEntity.getId(), copiedEntity.getId());
        assertEquals(salaryEntity.getBasicSalary(), copiedEntity.getBasicSalary());
        assertEquals(salaryEntity.getAllowances(), copiedEntity.getAllowances());
        assertEquals(salaryEntity.getTaxPerYear(), copiedEntity.getTaxPerYear());
    }

    @Test
    void testHasDefault_BasicSalaryExactly25000() {
        salaryEntity.setBasicSalary(25000);
        salaryEntity.setAllowances(5000);
        CustomException exception = assertThrows(CustomException.class, salaryEntity::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Input Not Up To Standard", exception.getMessage());
    }

    @Test
    void testHasDefault_AllowancesExactlyMinus1() {
        salaryEntity.setBasicSalary(30000);
        salaryEntity.setAllowances(-1);
        CustomException exception = assertThrows(CustomException.class, salaryEntity::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Input Not Up To Standard", exception.getMessage());
    }

    @Test
    void testOnSave_TaxCalculation_Exactly300000() {
        salaryEntity.setBasicSalary(25000); // Monthly
        salaryEntity.setAllowances(0); // Monthly
        salaryEntity.onSave();
        double expectedTax = 0.0; // Total salary = 300000
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

    @Test
    void testOnSave_TaxCalculation_Exactly700000() {
        salaryEntity.setBasicSalary(58333); // Monthly
        salaryEntity.setAllowances(0); // Monthly
        salaryEntity.onSave();
        double expectedTax = (699996 - 300000.0) * 0.05; // Tax for the amount above 300000
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

    @Test
    void testOnSave_TaxCalculation_Exactly1000000() {
        salaryEntity.setBasicSalary(83333); // Monthly
        salaryEntity.setAllowances(0); // Monthly
        salaryEntity.onSave();
        double expectedTax = ((999996 - 700000) * 0.1) + (0.05 * 400000); // Tax for the amount above 700000
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

    @Test
    void testOnSave_TaxCalculation_Exactly1200000() {
        salaryEntity.setBasicSalary(100000); // Monthly
        salaryEntity.setAllowances(0); // Monthly
        salaryEntity.onSave();
        double expectedTax = (((1200000 - 1000000) * 0.15) + (0.1 * 300000) + (0.05 * 400000)); // Tax for the amount above 1000000
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

    @Test
    void testOnSave_TaxCalculation_Exactly1500000() {
        salaryEntity.setBasicSalary(125000); // Monthly
        salaryEntity.setAllowances(0); // Monthly
        salaryEntity.onSave();
        double expectedTax = (((1500000 - 1200000) * 0.2) + (0.15 * 200000) + (0.1 * 300000) + (0.05 * 400000)); // Tax for the amount above 1200000
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

    @Test
    void testOnSave_TaxCalculation_Above1500000() {
        salaryEntity.setBasicSalary(150000); // Monthly
        salaryEntity.setAllowances(0); // Monthly
        salaryEntity.onSave();
        double expectedTax = (((1800000 - 1500000) * 0.3) + (0.2 * 300000) + (0.15 * 200000) + (0.1 * 300000) + (0.05 * 400000)); // Tax for the amount above 1500000
        assertEquals(expectedTax, salaryEntity.getTaxPerYear());
    }

}
