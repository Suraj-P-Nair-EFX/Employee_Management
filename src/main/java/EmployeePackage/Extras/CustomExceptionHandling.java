package EmployeePackage.Extras;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class CustomExceptionHandling extends RuntimeException{

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) throws CustomException{
        APIResponse apiResponse = new APIResponse(400,"Invalid Input",null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<APIResponse> handleCustomException(CustomException ex){
        APIResponse apiResponse = new APIResponse(ex.getErrorCode(),ex.getMessage(),null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity globalExceptionHandler(Exception ex){
        APIResponse apiResponse = new APIResponse(500,ex.getMessage(),null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }




}
