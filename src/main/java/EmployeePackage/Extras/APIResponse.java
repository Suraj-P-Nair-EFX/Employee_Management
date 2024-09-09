package EmployeePackage.Extras;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class APIResponse<T> {
    private double errorCode;
    private String message;
    private T body;

}
