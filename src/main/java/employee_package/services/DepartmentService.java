package employee_package.services;
import employee_package.entities.DepartmentEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.extras.ValidationServices;
import employee_package.repositories.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService extends ValidationServices {
    @Autowired DepartmentRepo departmentRepo;

    @Autowired EncryptionService encryptionService;

    //CREATE DEPARTMENT //CLEAR
    public APIResponse<DepartmentEntity> createDepartment(DepartmentEntity department){
        if(departmentRepo.existsById(department.getId())){
            throw new CustomException(400,"Department Already Exists");
        }
        department.hasDefault();
        invalidCharCheck(department.toString());

        DepartmentEntity encryptedDepartment = encryptDepartment(department);

        return new APIResponse<>(200, "Department Created Successfully", decryptDepartment(departmentRepo.save(encryptedDepartment)));
    }

    //GET SINGLE DEPARTMENT DECRYPTED //CLEAR
    public APIResponse<DepartmentEntity> getDepartmentDecrypted(int id){
        DepartmentEntity department = departmentRepo.findById(id).orElseThrow(()-> new CustomException(404,"Department Not Found"));
        return new APIResponse<>(200, "Department Found Successfully", decryptDepartment(department));
    }

    //GET SINGLE DEPARTMENT ENCRYPTED //CLEAR
    public APIResponse<DepartmentEntity> getDepartmentEncrypted(int id){
        DepartmentEntity department = departmentRepo.findById(id).orElseThrow (()->new CustomException(404,"Department Not Found"));
        return new APIResponse<>(200, "Department Found Successfully", department);
    }

    //GET ALL DEPARTMENT //CLEAR
    public APIResponse<List<DepartmentEntity>> getAllDepartment(){
        List<DepartmentEntity> departments = departmentRepo.findAll();
        if(departments.isEmpty()){
            throw new CustomException(404,"No Departments To Show");
        }
        List<DepartmentEntity> decryptedDepartments = departments.stream()
                .map(this::decryptDepartment)
                .toList();
        return new APIResponse<>(200,"Departments Found Successfully",decryptedDepartments);
    }

    //DELETE DEPARTMENT //CLEAR
    public APIResponse<DepartmentEntity> deleteDepartment(int id){
        DepartmentEntity department = getDepartmentEncrypted(id).getBody();
        departmentRepo.delete(department);
        return new APIResponse<>(200,"Department Deleted Successfully",decryptDepartment(department));
    }

    //UPDATE DEPARTMENT //CLEAR
    public APIResponse<DepartmentEntity> updateDepartment(DepartmentEntity newDepartment, int id){
        if(!departmentRepo.existsById(id))
            throw new CustomException(404, "Department Doesn't Exist");
        newDepartment.setId(id);
        newDepartment.hasDefault();
        DepartmentEntity existingDepartment = departmentRepo.save(encryptDepartment(newDepartment));
        return new APIResponse<>(200,"Department updated Successfully",decryptDepartment(existingDepartment ));
    }

    //ENCRYPT DEPARTMENT //CLEAR
    private DepartmentEntity encryptDepartment(DepartmentEntity department) {
        DepartmentEntity encryptedDepartment = new DepartmentEntity();
        encryptedDepartment.setId(department.getId());
        encryptedDepartment.setName(encryptionService.encrypt(department.getName()));
        return encryptedDepartment;
    }

    //DECRYPT DEPARTMENT //CLEAR
    private DepartmentEntity decryptDepartment(DepartmentEntity encryptedDepartment) {
        DepartmentEntity decryptedDepartment = new DepartmentEntity();
        decryptedDepartment.setId(encryptedDepartment.getId());
        decryptedDepartment.setName(encryptionService.decrypt(encryptedDepartment.getName()));
        return decryptedDepartment;
    }

}