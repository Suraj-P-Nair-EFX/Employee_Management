package employee_package.extras;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationServiceTests {

    private final ValidationServices validationServices = new ValidationServices();

    @Test
    void invalidCharCheck_ShouldThrowCustomException_WhenInvalidCharacterPresent() {
        String inputWithInvalidChar = "Hello@World";
        CustomException exception = assertThrows(CustomException.class, () -> {
            validationServices.invalidCharCheck(inputWithInvalidChar);
        });
        assertEquals(400, exception.getErrorCode());
        assertEquals("Invalid Characters Present", exception.getMessage());
    }

    @Test
    void invalidCharCheck_ShouldNotThrowException_WhenNoInvalidCharacters() {
        String validInput = "HelloWorld";
        validationServices.invalidCharCheck(validInput); // Should not throw any exception
    }

    @Test
    void invalidCharCheck_ShouldThrowCustomException_WhenStringContainsMultipleInvalidCharacters() {

        String inputWithMultipleInvalidChars = "Hello#World@2023";
        CustomException exception = assertThrows(CustomException.class, () -> {
            validationServices.invalidCharCheck(inputWithMultipleInvalidChars);
        });
        assertEquals(400, exception.getErrorCode());
        assertEquals("Invalid Characters Present", exception.getMessage());
    }
}
