package EmployeePackage.Extras;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private double errorCode;
    private String message;
    public CustomException(double code,String s){
        super(s);
        this.errorCode = code;
        this.message = s;
    }
}