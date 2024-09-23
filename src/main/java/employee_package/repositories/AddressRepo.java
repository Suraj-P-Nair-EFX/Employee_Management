package employee_package.repositories;

import employee_package.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<AddressEntity,Integer> {
}
