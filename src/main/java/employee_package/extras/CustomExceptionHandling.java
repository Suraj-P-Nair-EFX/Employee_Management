package employee_package.extras;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class CustomExceptionHandling extends RuntimeException{

    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<APIResponse<Void>> handleHttpMessageNotReadableException(){
        APIResponse<Void> apiResponse = new APIResponse<>(400,"Invalid Input",null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<APIResponse<Void>> handleCustomException(CustomException ex){
        APIResponse<Void> apiResponse = new APIResponse<>(ex.getErrorCode(),ex.getMessage(),null);
        return ResponseEntity.status((int)ex.getErrorCode()).body(apiResponse);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<APIResponse<Void>> handleDataIntegrityViolationException(){
        APIResponse<Void> apiResponse = new APIResponse<>(500,"Cannot Delete Department Since Employees Exist",null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleNoResourceFoundException(){
        APIResponse<Void> apiResponse = new APIResponse<>(404,"Not Found",null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
