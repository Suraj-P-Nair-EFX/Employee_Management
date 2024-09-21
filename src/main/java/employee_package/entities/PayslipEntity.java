package employee_package.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import employee_package.extras.CustomException;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
public class PayslipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payslipId;

    private double deductions = -1;
    private double bonus = -1;

    private int presentDays = -1;
    private int totalDays = -1;

    private double finalSalary;

    private LocalDate date = null;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;


    public void hasDefault(){
        if(deductions <= -1 || bonus <= -1 || presentDays <= -1 || totalDays <= 0 || date == null)
            throw new CustomException(400.1,"Input Not Up To Standard");
    }

    @PrePersist
    public void onSave(){
        finalSalary = ((double)employee.getSalary().getBasicSalary() * presentDays/totalDays)
                + employee.getSalary().getAllowances()
                + bonus
                - deductions
                - employee.getSalary().getTaxPerYear()/12.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayslipEntity that)) return false;
        return (this.date.getYear() == that.date.getYear() && this.date.getMonth() == that.date.getMonth() && Objects.equals(employee, that.employee));
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, employee);
    }

    @Override
    public String toString() {
        return "PayslipEntity {" +
                "payslipId=" + payslipId +
                ", employee=" + employee +
                ", date=" + date +
                ", salaryDetails={" +
                "basicSalary=" + employee.getSalary().getBasicSalary() +
                ", allowances=" + employee.getSalary().getAllowances() +
                ", deductions=" + deductions +
                ", taxPerYear=" + employee.getSalary().getTaxPerYear() +
                ", bonus=" + bonus +
                "}" +
                ", attendance={" +
                "presentDays=" + presentDays +
                ", totalDays=" + totalDays +
                "}" +
                ", finalSalary=" + finalSalary +
                '}';
    }

}