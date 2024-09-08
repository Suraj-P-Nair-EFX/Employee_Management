package EmployeePackage.Repositories;


import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Entities.PayslipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayslipRepo extends JpaRepository<PayslipEntity,Integer> {
    List<PayslipEntity> findByemployee(EmployeeEntity id);
}
