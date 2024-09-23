package employee_package.repositories;

import employee_package.entities.EmployeeEntity;
import employee_package.entities.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepo extends JpaRepository<SalaryEntity, Integer> {

        SalaryEntity findByEmployee(EmployeeEntity employee);
}
