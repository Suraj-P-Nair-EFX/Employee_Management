package employee_package.entities;

import employee_package.extras.CustomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DepartmentEntityTests {

    @Test
    void hasDefault_ShouldThrowCustomException_WhenIdOrNameIsNull() {
        DepartmentEntity department = new DepartmentEntity(null, "HR"); // id is null
        CustomException exception = assertThrows(CustomException.class, department::hasDefault);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Has Default DepartmentEntity", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenBothFieldsAreNull() {
        DepartmentEntity department = new DepartmentEntity(null, null); // Both fields are null
        CustomException exception = assertThrows(CustomException.class, department::hasDefault);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Has Default DepartmentEntity", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldNotThrowException_WhenAllFieldsArePresent() {
        DepartmentEntity department = new DepartmentEntity(1, "HR"); // Both fields are set
        department.hasDefault();
        assertEquals(1, department.getId());
        assertEquals("HR", department.getName());
    }

    @Test
    void getId_ShouldThrowCustomException_WhenIdIsNull() {
        DepartmentEntity department = new DepartmentEntity(null, "HR"); // id is null
        CustomException exception = assertThrows(CustomException.class, department::getId);
        assertEquals(200.1, exception.getErrorCode());
        assertEquals("Department ID not Entered", exception.getMessage());
    }

    @Test
    void getId_ShouldReturnId_WhenIdIsPresent() {
        DepartmentEntity department = new DepartmentEntity(1, "HR"); // id is set
        Integer id = department.getId();
        assertEquals(1, id);
    }
}
