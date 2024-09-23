package employee_package.extras;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionTests {

    @Test
    void customException_ShouldInitializeFieldsCorrectly() {
        double expectedErrorCode = 404;
        String expectedMessage = "Not Found";

        CustomException exception = new CustomException(expectedErrorCode, expectedMessage);

        assertEquals(expectedErrorCode, exception.getErrorCode());
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedMessage, exception.getMessage()); // Check the message from RuntimeException
    }
}
