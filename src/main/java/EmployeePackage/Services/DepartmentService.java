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

    @Autowired
    EncryptionService encryptionService;

    public APIResponse CreateDepartment(DepartmentEntity department){
        department.hasDefault();
        invalidCharCheck(department.toString());
        if(departmentRepo.existsById(department.getId())){
            throw new CustomException(400,"Department Already Exists");
        }
        DepartmentEntity encryptedDepartment = encryptDepartment(department);

        return new APIResponse<>(200, "Department Created Successfully", departmentRepo.save(encryptedDepartment));
    }

    public APIResponse GetDepartment(int id){
        if(!departmentRepo.existsById(id)){
            throw new CustomException(200.1,"Department Doesn't Exist");
        }
        DepartmentEntity encryptedDepartment = departmentRepo.findById(id).get();
        DepartmentEntity decryptedDepartment = decryptDepartment(encryptedDepartment);
        return new APIResponse<>(200, "Department Found Successfully", decryptedDepartment);    }

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

    private DepartmentEntity encryptDepartment(DepartmentEntity department) {
        DepartmentEntity encryptedDepartment = new DepartmentEntity();
        encryptedDepartment.setId(department.getId());
        encryptedDepartment.setName(encryptionService.encrypt(department.getName()));
        return encryptedDepartment;
    }

    private DepartmentEntity decryptDepartment(DepartmentEntity encryptedDepartment) {
        DepartmentEntity decryptedDepartment = new DepartmentEntity();
        decryptedDepartment.setId(encryptedDepartment.getId());
        decryptedDepartment.setName(encryptionService.decrypt(encryptedDepartment.getName()));
        return decryptedDepartment;
    }

}