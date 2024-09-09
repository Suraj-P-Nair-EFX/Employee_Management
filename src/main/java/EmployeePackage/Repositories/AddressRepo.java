package EmployeePackage.Repositories;

import EmployeePackage.Entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<AddressEntity,Integer> {
}