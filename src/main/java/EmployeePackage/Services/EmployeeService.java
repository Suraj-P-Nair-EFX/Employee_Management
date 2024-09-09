package EmployeePackage.Services;
import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Extras.ValidationServices;
import EmployeePackage.Repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends ValidationServices {
    @Autowired EmployeeRepo employeeRepo;
    @Autowired DepartmentService departmentService;

    public APIResponse createEmployeeService(EmployeeEntity employeeEntity){
        APIResponse apiResponse = departmentService.GetDepartment(employeeEntity.getDepartment().getId());
        if(apiResponse.getBody()==null) return apiResponse;
        employeeEntity.setDepartment((DepartmentEntity)apiResponse.getBody());

        if(employeeEntity.hasDefault()) return new APIResponse<>(200.1,"Full Details Not Provided", null);
        if(invalidCharCheck(employeeEntity.toString())) return new APIResponse<>(200.1,"Invalid Characters Present", null);
        if(employeeRepo.existsById(employeeEntity.getId())) return new APIResponse<>(200.1,"Employee Already Exist", null);

        return new APIResponse<>(200,"Employee Created Successfully", employeeRepo.save(employeeEntity));
    }

    public APIResponse getAllEmployeeService(){
        if(employeeRepo.count()==0) return new APIResponse<>(200,"No Employees Found", null);
        return new APIResponse<>(200,"Employees Found Successfully", employeeRepo.findAll());

    }

    public APIResponse getEmployeeById(int id){
        if(!employeeRepo.existsById(id)) return new APIResponse<>(200.1,"No Employee Found", null);
        EmployeeEntity employee = employeeRepo.findById(id).get();
        return new APIResponse<>(200,"Employee Found Successfully", employee);
    }

    public APIResponse deleteEmployee(int id){
        if(employeeRepo.existsById(id)){
            EmployeeEntity employee = employeeRepo.findById(id).get();
            employeeRepo.deleteById(id);
            return new APIResponse<>(200,"Employee Deleted Successfully", (employee));
        }
        else{
            return new APIResponse<>(200.1,"Employee Not Found", null);
        }
    }

    public APIResponse updateEmployee(EmployeeEntity employeeEntity, int id){
        APIResponse apiResponse = getEmployeeById(id);
        if(apiResponse.getErrorCode()== 200) {
            EmployeeEntity existingEntity = ((EmployeeEntity) apiResponse.getBody());

            existingEntity.updateEntity(employeeEntity);

            apiResponse = departmentService.GetDepartment(employeeEntity.getDepartment().getId());
            if(apiResponse.getBody()==null) return apiResponse;

            existingEntity.setDepartment((DepartmentEntity)apiResponse.getBody());
            employeeRepo.save(existingEntity);
            return new APIResponse<>(200,"Employee Updated Successfully", existingEntity);
        }
        return apiResponse;
    }

    public boolean isExistById(int id){
        return employeeRepo.existsById(id);
    }

    public boolean isExistByEntity(EmployeeEntity employeeEntity){
        return employeeRepo.existsById(employeeEntity.getId());
    }
}