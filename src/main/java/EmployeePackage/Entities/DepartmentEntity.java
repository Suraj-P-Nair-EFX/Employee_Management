package EmployeePackage.Entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DepartmentEntity {


    @Id
    private Integer id = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name = null;


    public boolean hasDefault(){return id == null  || name == null;}
    public void setPartialNull(){
        name = null;
    }
}
