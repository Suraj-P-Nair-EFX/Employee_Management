package employee_package.extras;
import employee_package.entities.EmployeeEntity;
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