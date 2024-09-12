package EmployeePackage.Entities;
import EmployeePackage.Extras.CustomException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class EmployeeEntity {

    @Id
    @Getter
    private Integer id = null;
    @Getter
    private String name = null;
    @Getter
    private Integer age = null;

    @JoinColumn(name = "address", referencedColumnName = "id")
    @OneToOne
    @Cascade(CascadeType.ALL)
    private AddressEntity address;

    @PrimaryKeyJoinColumn
    @ManyToOne
    private DepartmentEntity department;

    @JsonIgnore
    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "employee", orphanRemoval = true)
    private List<PayslipEntity> payslips;

    public void setPartialNull(){
        CompletableFuture.runAsync(department::setPartialNull);
        CompletableFuture.runAsync(address::setPartialNull);
    }

    public void updateEntity(EmployeeEntity newEntity) {
        Optional.ofNullable(newEntity.getName()).ifPresent(this::setName);
        //Optional.ofNullable(newEntity.getDepartment()).ifPresent(this::setDepartment);
        Optional.ofNullable(newEntity.getAge()).ifPresent(this::setAge);
        //Optional.ofNullable(newEntity.getAddress()).ifPresent(this::setAddress);
    }

    public void hasDefault(){
        CompletableFuture.runAsync(department::hasDefault);

        if(getAddress().getClass() == AddressEntity.class)
            CompletableFuture.runAsync(address::hasDefault);
        if(name == null || age == null || id == null){
            throw new CustomException(200.1,"Default values Employee");
        }
    }


    public AddressEntity getAddress() {
        if(address == null)
            throw new CustomException(200.1,"Address Not Entered");
        return address;
    }

    public DepartmentEntity getDepartment() {
        if(department == null){
            throw new CustomException(200.1,"Department Not Entered");
        }
        return department;
    }

}
