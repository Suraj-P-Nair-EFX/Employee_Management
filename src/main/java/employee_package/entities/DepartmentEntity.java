package employee_package.entities;

import employee_package.extras.CustomException;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentEntity {

    @Id
    private Integer id = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    private String name = null;

    public void hasDefault(){
        if(id == null  || name == null){
            throw new CustomException(200.1,"Has Default DepartmentEntity");
        }
    }



    public Integer getId() {
        if(id==null){
            throw new CustomException(200.1,"Department ID not Entered");
        }
        return id;
    }

}
