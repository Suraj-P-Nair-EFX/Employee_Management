package employee_package.repositories;

import employee_package.entities.EmployeeEntity;
import employee_package.entities.PayslipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PayslipRepo extends JpaRepository<PayslipEntity,Integer> {
    List<PayslipEntity> findByEmployee(EmployeeEntity id);
    PayslipEntity findByEmployeeAndMonth(EmployeeEntity employeeEntity, String month);
}