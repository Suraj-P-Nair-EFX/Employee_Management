package EmployeePackage.Services;
import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Repositories.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepo departmentRepo;

    public DepartmentEntity CreateDepartment(DepartmentEntity department){
        return departmentRepo.save(department);
    }
    public APIResponse GetDepartment(int id){
        if(!departmentRepo.existsById(id)){
            return new APIResponse<>(200.1,"Department Doesn't Exist", null);
        }
        return new APIResponse<>(200,"Department Created Successfully", departmentRepo.findById(id).get());
    }
}
