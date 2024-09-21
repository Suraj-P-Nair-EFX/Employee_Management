package employee_package.repositories;

import employee_package.entities.EmployeeEntity;
import employee_package.entities.PayslipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PayslipRepo extends JpaRepository<PayslipEntity,Integer> {
    List<PayslipEntity> findByEmployeeId(int id);
    Page<PayslipEntity> findAllByEmployeeId(int id, Pageable page);

    PayslipEntity findByEmployeeAndDate(EmployeeEntity employeeEntity, LocalDate date);
    Page<PayslipEntity> findByEmployeeAndDateBetween(EmployeeEntity employee,LocalDate from,LocalDate to,Pageable page);
}