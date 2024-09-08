package EmployeePackage.Entities;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeEntity {

    @Id
    private int id = -1;
    private String name = null;
    private int age = -1;

    @JoinColumn(name = "address", referencedColumnName = "id")
    @OneToOne
    @Cascade(CascadeType.ALL)
    private AddressEntity address;

    @PrimaryKeyJoinColumn
    @ManyToOne
    @Cascade(CascadeType.ALL)
    private DepartmentEntity department;

//    @JsonBackReference
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//    private List<PayslipEntity> payslipId;

    public void setPartialNull(){

        department.setPartialNull();
        address.setPartialNull();
    }


    public boolean hasDefault(){
        return name == null || age == -1 || id == -1 || address == null || department == null;
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
