package EmployeePackage.Repositories;

import EmployeePackage.Entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<DepartmentEntity,Integer> {
}