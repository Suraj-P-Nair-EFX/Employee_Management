package employee_package.extras;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlingTests {

    private final CustomExceptionHandling exceptionHandling = new CustomExceptionHandling();

    @Test
    void handleHttpMessageNotReadableException_ShouldReturnBadRequest() {
        ResponseEntity<APIResponse<Void>> response = exceptionHandling.handleHttpMessageNotReadableException();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400.1, Objects.requireNonNull(response.getBody()).getErrorCode());
        assertEquals("Invalid Input", response.getBody().getMessage());
    }

    @Test
    void handleCustomException_ShouldReturnCustomErrorCodeAndMessage() {
        CustomException customException = new CustomException(403, "Forbidden");
        ResponseEntity<APIResponse<Void>> response = exceptionHandling.handleCustomException(customException);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(403, Objects.requireNonNull(response.getBody()).getErrorCode());
        assertEquals("Forbidden", response.getBody().getMessage());
    }

    @Test
    void handleDataIntegrityViolationException_ShouldReturnInternalServerError() {
        ResponseEntity<APIResponse<Void>> response = exceptionHandling.handleDataIntegrityViolationException();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, Objects.requireNonNull(response.getBody()).getErrorCode());
        assertEquals("Cannot Delete Department Since Employees Exist", response.getBody().getMessage());
    }

    @Test
    void handleNoResourceFoundException_ShouldReturnNotFound() {
        ResponseEntity<APIResponse<Void>> response = exceptionHandling.handleNoResourceFoundException();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, Objects.requireNonNull(response.getBody()).getErrorCode());
        assertEquals("Not Found", response.getBody().getMessage());
    }
}
