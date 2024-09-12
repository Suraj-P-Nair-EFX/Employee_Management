package EmployeePackage.Services;
import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Extras.CustomException;
import EmployeePackage.Extras.ValidationServices;
import EmployeePackage.Repositories.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends ValidationServices {
    @Autowired
    DepartmentRepo departmentRepo;

    public APIResponse CreateDepartment(DepartmentEntity department){
        department.hasDefault();
        invalidCharCheck(department.toString());
        if(departmentRepo.existsById(department.getId())){
            throw new CustomException(400,"Department Already Exists");
        }
        return new APIResponse<>(200, "Department Created Successfully", departmentRepo.save(department));
    }

    public APIResponse GetDepartment(int id){
        if(!departmentRepo.existsById(id)){
            throw new CustomException(200.1,"Department Doesn't Exist");
        }
        return new APIResponse<>(200,"Department Found Successfully", departmentRepo.findById(id).get());
    }

    public APIResponse GetAllDepartment(){
        List<DepartmentEntity> departments = departmentRepo.findAll();
        if(departments.isEmpty()){
            throw new CustomException(400,"No Departments To Show");
        }
        return new APIResponse<>(200,"Departments Found Successfully",departments);

    }

    public APIResponse DeleteDepartment(int id){
        DepartmentEntity department = (DepartmentEntity) GetDepartment(id).getBody();
        departmentRepo.delete(department);
        return new APIResponse<>(200,"Department Found Successfully",department );
    }


}