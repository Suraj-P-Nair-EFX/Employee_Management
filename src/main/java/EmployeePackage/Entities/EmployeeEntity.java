package EmployeePackage.Entities;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeEntity {

    @Id
    private Integer id = null;
    private String name = null;
    private Integer age = null;

    @JoinColumn(name = "address", referencedColumnName = "id")
    @OneToOne
    @Cascade(CascadeType.ALL)
    private AddressEntity address;

    @PrimaryKeyJoinColumn
    @ManyToOne
    @Cascade(CascadeType.ALL)
    private DepartmentEntity department;

    public void setPartialNull(){

        department.setPartialNull();
        address.setPartialNull();
    }

    public void updateEntity(EmployeeEntity newEntity){
        if (newEntity.getName()!=null){
            this.name = newEntity.getName();
        }
        if(newEntity.getDepartment() != null){
            this.department = newEntity.getDepartment();
        }
        if(newEntity.getAge() != null){
            this.age = newEntity.getAge();
        }

        if(newEntity.getAge() != null){
            this.address = newEntity.getAddress();
        }
    }

    public boolean hasDefault(){
        return name == null || age == null || id == null || address == null || department == null || department.hasDefault() || address.hasDefault();
    }

    public Object thenReturn(EmployeeEntity employee) {
        return employee;
    }

    public EmployeeEntity(int employeeId, String name) {
        this.id = employeeId;
        this.name = name;
    }

    public EmployeeEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public EmployeeEntity(int employeeId, int age) {
        this.id = employeeId;
        this.age = age;
    }

    public EmployeeEntity(int employeeId, String name, int age) {
        this.id = employeeId;
        this.name = name;
        this.age = age;
    }


}
