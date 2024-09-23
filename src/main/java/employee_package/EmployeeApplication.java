package employee_package;

import employee_package.entities.AddressEntity;
import employee_package.entities.DepartmentEntity;
import employee_package.entities.EmployeeEntity;
import employee_package.extras.ValidationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static employee_package.extras.ValidationServices.invalidCharCheck;

@SpringBootApplication
public class EmployeeApplication {


	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}