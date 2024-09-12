package EmployeePackage.Entities;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Objects;

@Entity
@Data
public class PayslipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payslipId;
    private double basicSalary = -1;
    private double allowance = -1;
    private double deductions = -1;
    private double bonus = -1;
    private double tax = -1;
    private int presentDays = -1;
    private int totalDays = -1;
    private double finalSalary;
    private String month = null;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    public boolean hasDefault(){
        return basicSalary == -1 || allowance == -1 || deductions == -1 || bonus == -1 || presentDays == -1 || totalDays == -1 || month == null;
    }

    @PrePersist
    public void onSave(){
        double totalSalary = basicSalary * 12;

        if(totalSalary <= 3_00_000){tax = 0;}
        else if (totalSalary <= 7_00_000){tax = (totalSalary-3_00_000) * 0.05;}
        else if (totalSalary <= 10_00_000){tax = ((totalSalary-7_00_000) * 0.1) + (0.05 * 4_00_000);}
        else if (totalSalary <= 12_00_000){tax = (((totalSalary-10_00_000) * 0.15) + (0.1*3_00_000) + (0.05*4_00_000));}
        else if(totalSalary <= 15_00_000){tax = (((totalSalary-12_00_000) * 0.2) + (2_00_000 * 0.15) + (0.1 * 3_00_000) + (0.05 * 4_00_000));}
        else{tax = (((totalSalary-15_00_000) * 0.3) + (0.2 * 3_00_000) + (2_00_000 * 0.15) + (0.1 * 3_00_000) + (0.05 * 4_00_000));}

        tax /= 12;


        finalSalary = basicSalary*(presentDays/totalDays) + allowance - deductions + bonus -tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayslipEntity that)) return false;
        return Objects.equals(month, that.month) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, employee);
    }
}