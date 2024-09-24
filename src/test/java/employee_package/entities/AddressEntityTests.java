package employee_package.entities;

import employee_package.extras.CustomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressEntityTests {

    @Test
    void hasDefault_ShouldThrowCustomException_WhenAnyFieldIsNull() {
        AddressEntity address = new AddressEntity(1, null, "City", 12345); // primAddress is null
        CustomException exception = assertThrows(CustomException.class, address::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Has Default in Address", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldThrowCustomException_WhenAllFieldsAreNull() {
        AddressEntity address = new AddressEntity(1, null, null, null); // All fields are null
        CustomException exception = assertThrows(CustomException.class, address::hasDefault);
        assertEquals(400.1, exception.getErrorCode());
        assertEquals("Has Default in Address", exception.getMessage());
    }

    @Test
    void hasDefault_ShouldNotThrowException_WhenAllFieldsArePresent() {
        AddressEntity address = new AddressEntity(1, "123 Main St", "City", 12345);
        address.hasDefault();
        assertEquals("123 Main St", address.getPrimAddress());
        assertEquals("City", address.getCity());
        assertEquals(12345, address.getPincode());
    }
}
