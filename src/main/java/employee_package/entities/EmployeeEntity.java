package employee_package.entities;
import com.fasterxml.jackson.annotation.JsonInclude;
import employee_package.extras.CustomException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToOne(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private AddressEntity address;

    @PrimaryKeyJoinColumn
    @ManyToOne
    private DepartmentEntity department;

    @JsonIgnore
    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "employee", orphanRemoval = true)
    @Getter
    private List<PayslipEntity> payslips;

    @JsonIgnore
    @OneToOne(mappedBy = "employee",orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @Getter
    private SalaryEntity salary;


    public void hasDefault(){
        getDepartment();
        //getAddress();
        address.hasDefault();
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

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address.getId() +
                ", department=" + department.getId() +
                '}';
    }

    public EmployeeEntity(EmployeeEntity employee) {
        this.id = employee.id;
        this.name = employee.name;
        this.age = employee.age;
        this.address = employee.getAddress();
        this.department = employee.getDepartment();
        this.payslips = employee.payslips;
        this.salary = employee.salary;
    }
}
