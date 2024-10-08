package employee_package.entities;
import employee_package.extras.CustomException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalaryEntity {
    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "employee",referencedColumnName = "id")
    private EmployeeEntity employee;

    private Integer basicSalary = -1;
    private Integer allowances = -1;
    private Double taxPerYear;

    public void hasDefault(){
                if((basicSalary<=25000) || allowances <=-1)
                    throw new CustomException(400.1,"Input Not Up To Standard");
    }

    @PrePersist
    public void onSave(){
        double totalSalary = basicSalary * 12.0;

        if(totalSalary <= 3_00_000){
            taxPerYear = 0.0;}
        else if (totalSalary <= 7_00_000){
            taxPerYear = (totalSalary-3_00_000) * 0.05;}
        else if (totalSalary <= 10_00_000){
            taxPerYear = ((totalSalary-7_00_000) * 0.1) + (0.05 * 4_00_000);}
        else if (totalSalary <= 12_00_000){
            taxPerYear = (((totalSalary-10_00_000) * 0.15) + (0.1*3_00_000) + (0.05*4_00_000));}
        else if(totalSalary <= 15_00_000){
            taxPerYear = (((totalSalary-12_00_000) * 0.2) + (2_00_000 * 0.15) + (0.1 * 3_00_000) + (0.05 * 4_00_000));}
        else{
            taxPerYear = (((totalSalary-15_00_000) * 0.3) + (0.2 * 3_00_000) + (2_00_000 * 0.15) + (0.1 * 3_00_000) + (0.05 * 4_00_000));}

    }

    public SalaryEntity(SalaryEntity other) {
        this.id = other.id;
        this.employee = other.getEmployee(); // Assuming EmployeeEntity has a proper copy constructor or is immutable
        this.basicSalary = other.basicSalary;
        this.allowances = other.allowances;
        this.taxPerYear = other.taxPerYear;
    }

}
