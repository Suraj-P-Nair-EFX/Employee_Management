package employee_package.repositories;

import employee_package.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<DepartmentEntity,Integer> {
}