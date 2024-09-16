package EmployeePackage.Services;
import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Extras.CustomException;
import EmployeePackage.Extras.ValidationServices;
import EmployeePackage.Repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeService extends ValidationServices {
    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    DepartmentService departmentService;

    public APIResponse createEmployeeService(EmployeeEntity employeeEntity){

        APIResponse apiResponse = departmentService.GetDepartment(employeeEntity.getDepartment().getId());
        employeeEntity.setDepartment((DepartmentEntity)apiResponse.getBody());

        CompletableFuture.runAsync(employeeEntity::hasDefault);
        CompletableFuture.runAsync(() -> invalidCharCheck(employeeEntity.toString()));

        if(employeeRepo.existsById(employeeEntity.getId())) return new APIResponse<>(200.1,"Employee Already Exist", null);
        return new APIResponse<>(200,"Employee Created Successful", employeeRepo.save(employeeEntity));
    }

    public APIResponse getAllEmployeeService(){
        List<EmployeeEntity> employees = employeeRepo.findAll();
        if (employees.isEmpty()) {
            return new APIResponse<>(200, "No Employees Found", null);
        }
        return new APIResponse<>(200, "Employees Found Successfully", employees);
    }

    public APIResponse getEmployeeById(int id){
        EmployeeEntity employee = employeeRepo.findById(id).orElse(null);
        if (employee == null) {
            return new APIResponse<>(200.1, "No Employee Found", null);
        }
        return new APIResponse<>(200, "Employee Found Successfully", employee);
    }

    public APIResponse deleteEmployee(int id){
        Optional<EmployeeEntity> optionalEmployee = employeeRepo.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepo.deleteById(id);
            return new APIResponse<>(200, "Employee Deleted Successfully", optionalEmployee.get());
        } else {
            return new APIResponse<>(200.1, "Employee Not Found", null);
        }
    }

    public APIResponse updateEmployee(EmployeeEntity newEmployee, int id) {
        EmployeeEntity existingEmployee = employeeRepo.findById(id)
                .orElseThrow(() -> new CustomException(404,"Employee Not Found"));
        newEmployee.setId(existingEmployee.getId());
        newEmployee.setPayslips(existingEmployee.getPayslips());
        newEmployee.hasDefault();
        employeeRepo.save(newEmployee);

        return new APIResponse<>(200, "Employee Updated Successfully", newEmployee);
    }

    public boolean isExistById(int id){
        return employeeRepo.existsById(id);
    }

}