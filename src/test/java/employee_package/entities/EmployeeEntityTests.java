package employee_package.entities;

import employee_package.extras.CustomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeEntityTests {

    @Test
    void hasDefault_ShouldThrowCustomException_WhenAnyFieldIsNull() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(null, null, null, address, department, null, null);
        CustomException exception = assertThrows(CustomException.class, employee::hasDefault);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Default values Employee", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldNotThrowException_WhenAllFieldsArePresent() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department, null, null);
        employee.hasDefault();
        assertEquals(1, employee.getId());
        assertEquals("John Doe", employee.getName());
        assertEquals(30, employee.getAge());
    }

    @Test
    void getAddress_ShouldThrowCustomException_WhenAddressIsNull() {
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, null, department, null, null);
        CustomException exception = assertThrows(CustomException.class, employee::getAddress);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Address Not Entered", exception.getMessage());
    }

    @Test
    void getDepartment_ShouldThrowCustomException_WhenDepartmentIsNull() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, null, null, null);
        CustomException exception = assertThrows(CustomException.class, employee::getDepartment);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Department Not Entered", exception.getMessage());
    }

    @Test
    void getDepartment_ShouldReturnDepartment_WhenDepartmentIsPresent() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department, null, null);
        DepartmentEntity result = employee.getDepartment();
        assertEquals(department, result);
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenAddressHasDefault() {
        // Create an AddressEntity that will throw an exception when hasDefault is called
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345) {
            @Override
            public void hasDefault() {
                throw new CustomException(300.1, "Address has default values");
            }
        };

        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", 30, address, department, null, null);

        CustomException exception = assertThrows(CustomException.class, employee::hasDefault);
        assertEquals(300.1, exception.getErrorCode());
        assertEquals("Address has default values", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenIdIsNull() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(null, "John Doe", 30, address, department, null, null);

        CustomException exception = assertThrows(CustomException.class, employee::hasDefault);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Default values Employee", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenNameIsNull() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, null, 30, address, department, null, null);

        CustomException exception = assertThrows(CustomException.class, employee::hasDefault);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Default values Employee", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenAgeIsNull() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        DepartmentEntity department = new DepartmentEntity(1, "HR");
        EmployeeEntity employee = new EmployeeEntity(1, "John Doe", null, address, department, null, null);

        CustomException exception = assertThrows(CustomException.class, employee::hasDefault);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Default values Employee", exception.getMessage());
    }

}
