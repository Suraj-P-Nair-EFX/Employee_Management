package EmployeePackage.Services;
import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Extras.ValidationServices;
import EmployeePackage.Repositories.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends ValidationServices {
    @Autowired
    DepartmentRepo departmentRepo;

    public APIResponse CreateDepartment(DepartmentEntity department){
        if(department.hasDefault()) return new APIResponse<>(200.1,"Full Details Not Provided", null);
        if(invalidCharCheck(department.toString())) return new APIResponse<>(200.1,"Invalid Characters Present", null);
        if(departmentRepo.existsById(department.getId())) return new APIResponse<>(200.1,"Department Already Exists", null);
        return new APIResponse<>(200,"Employee Created Successfully", departmentRepo.save(department));
    }

    public APIResponse GetDepartment(int id){
        if(!departmentRepo.existsById(id)){
            return new APIResponse<>(200.1,"Department Doesn't Exist", null);
        }
        return new APIResponse<>(200,"Department Created Successfully", departmentRepo.findById(id).get());
    }
}
