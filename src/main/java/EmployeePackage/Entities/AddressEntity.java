package EmployeePackage.Entities;


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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String primAddress = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pincode = null;

    boolean hasDefault(){return pincode == null || city == null || primAddress == null;}

    public void setPartialNull(){
        primAddress = null;
        city = null;
        pincode = null;
    }
}
