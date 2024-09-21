package employee_package.entities;
import employee_package.extras.CustomException;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressEntity {
    @Id
    private int id ;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String primAddress = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pincode = null;

    public void hasDefault(){
        if(pincode == null || city == null || primAddress == null){
            throw new CustomException(200.1,"Has Default in Address");
        }
    }
}
