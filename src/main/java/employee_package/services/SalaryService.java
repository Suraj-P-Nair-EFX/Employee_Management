package employee_package.services;


import employee_package.entities.EmployeeEntity;
import employee_package.entities.SalaryEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.repositories.SalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {
    @Autowired EmployeeService employeeService;
    @Autowired SalaryRepo salaryRepo;

    public APIResponse<SalaryEntity> createSalary(SalaryEntity salary, int id) {
        EmployeeEntity employee = employeeService.getEmployeeByIdEncrypted(id).getBody();
        if (salaryRepo.existsByEmployee(employee))
            throw new CustomException(400, "Salary already exists for this employee");
        salary.setEmployee(employee);
        salary.hasDefault();
        SalaryEntity savedSalary = salaryRepo.save(salary);
        return new APIResponse<>(200, "Added salary successfully", decryptSalary(savedSalary));
    }

    public APIResponse<SalaryEntity> getSalaryDecrypt(int id){
        SalaryEntity salary = salaryRepo.findByEmployeeId(id).orElseThrow (()->new CustomException(404,"Employee Not Found"));
        if(salary == null)
            throw new CustomException(404,"Salary Not Found");
        return new APIResponse<>(200,"Salary Retrieved Successfully",decryptSalary(salary));
    }

    public SalaryEntity decryptSalary(SalaryEntity salary){

        EmployeeEntity employee = salary.getEmployee();
        EmployeeEntity decryptedEmployee = new EmployeeEntity(employee);
        salary.setEmployee(employeeService.decryptEmployee(decryptedEmployee));
        return salary;
    }
}
