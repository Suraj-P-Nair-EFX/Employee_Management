package employee_package.services;

import employee_package.entities.EmployeeEntity;
import employee_package.entities.SalaryEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.repositories.SalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {
    @Autowired EmployeeService employeeService;
    @Autowired SalaryRepo salaryRepo;
    String SALARY_NOT_FOUND = "Salary Not Found";

    public APIResponse<SalaryEntity> createSalary(SalaryEntity salary, int id) {
        EmployeeEntity employee = employeeService.getEmployeeByIdEncrypted(id).getBody();
        if (salaryRepo.existsById(id))
            throw new CustomException(400, "Salary already exists for this employee");
        salary.setId(id);
        salary.setEmployee(employee);
        salary.hasDefault();
        SalaryEntity savedSalary = salaryRepo.save(salary);
        return new APIResponse<>(200, "Added salary successfully", decryptSalary(savedSalary));
    }

    public APIResponse<SalaryEntity> getSalaryDecrypt(int id){
        SalaryEntity salary = salaryRepo.findById(id).orElseThrow (()->new CustomException(404,SALARY_NOT_FOUND));
        return new APIResponse<>(200,"Salary Retrieved Successfully",decryptSalary(salary));
    }

    public APIResponse<List<SalaryEntity>> getALlSalaries(){
        List<SalaryEntity> salaries = salaryRepo.findAll();
        if(salaries.isEmpty())
            throw new CustomException(404,SALARY_NOT_FOUND);
        List<SalaryEntity> decryptedSalaries = salaries.stream()
                .map(this::decryptSalary)
                .toList();

        return new APIResponse<>(200, "Retrieved Salaries Successfully", decryptedSalaries);
    }

    public APIResponse<SalaryEntity> deleteSalary(int id){
        SalaryEntity salary = salaryRepo.findById(id).orElseThrow (()->new CustomException(404,SALARY_NOT_FOUND));
        salaryRepo.delete(salary);
        return new APIResponse<>(200,"Salary Deleted Successfully",decryptSalary(salary));
    }

    public APIResponse<SalaryEntity> updateSalary(SalaryEntity newSalary, int id){

        SalaryEntity existingSalary = salaryRepo.findById(id).orElseThrow(()-> new CustomException(404,SALARY_NOT_FOUND));
        existingSalary.setBasicSalary(newSalary.getBasicSalary());
        existingSalary.setAllowances(newSalary.getAllowances());
        salaryRepo.save(existingSalary);
        return new APIResponse<>(200,"Salary Updated Successfully",decryptSalary(existingSalary));
    }

    public SalaryEntity decryptSalary(SalaryEntity salary){
        EmployeeEntity employee = salary.getEmployee();
        EmployeeEntity decryptedEmployee = new EmployeeEntity(employee);
        salary.setEmployee(employeeService.decryptEmployee(decryptedEmployee));
        return salary;
    }
}
