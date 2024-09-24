package employee_package.services;
import employee_package.entities.AddressEntity;
import employee_package.entities.DepartmentEntity;
import employee_package.entities.EmployeeEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.extras.ValidationServices;
import employee_package.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService extends ValidationServices {
    @Autowired EmployeeRepo employeeRepo;
    @Autowired DepartmentService departmentService;
    @Autowired  EncryptionService encryptionService;

    //CREATE EMPLOYEE //CLEAR
    public APIResponse<EmployeeEntity> createEmployeeService(EmployeeEntity employeeEntity){

        APIResponse<DepartmentEntity> apiResponse = departmentService.getDepartmentDecrypted(employeeEntity.getDepartment().getId());
        employeeEntity.getAddress().setId(employeeEntity.getId());
        employeeEntity.setDepartment(apiResponse.getBody());
        employeeEntity.hasDefault();
        invalidCharCheck(employeeEntity.toString());
        if(employeeRepo.existsById(employeeEntity.getId())) throw new CustomException(400,"Employee Already Exists");
        return new APIResponse<>(200,"Employee Created Successfully",decryptEmployee(employeeRepo.save(encryptEmployee(employeeEntity))));
    }

    //GET ALL EMPLOYEES //CLEAR
    public APIResponse<List<EmployeeEntity>> getAllEmployeeService(int page, int size){

        if (page < 0) {
            throw new CustomException(404, "Page number must be non-negative");
        }
        if(size<=0){
            throw new CustomException(404,"Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeEntity> employeePage = employeeRepo.findAll(pageable);

        if (page >= employeePage.getTotalPages() && employeePage.getTotalPages() > 0) {
            throw new CustomException(404, "Not Found");
        }

        if (employeePage.isEmpty()) {
            throw new CustomException(404, "No Employees Found");
        }

        List<EmployeeEntity> decryptedEmployees = employeePage.getContent().stream()
                .map(this::decryptEmployee)
                .toList();
        return new APIResponse<>(200, "Page " + page + " Found Successfully" , decryptedEmployees);
    }

    //GET SINGLE EMPLOYEE ENCRYPTED //CLEAR
    public APIResponse<EmployeeEntity> getEmployeeByIdDecrypted(int id){
        EmployeeEntity employee = employeeRepo.findById(id).orElseThrow (()->new CustomException(404,"Employee Not Found"));
        return new APIResponse<>(200, "Employee Found Successfully", decryptEmployee(employee));
    }

    //GET SINGLE EMPLOYEE DECRYPTED //CLEAR
    public APIResponse<EmployeeEntity> getEmployeeByIdEncrypted(int id){
        EmployeeEntity employee = employeeRepo.findById(id).orElseThrow (()->new CustomException(404,"Employee Not Found"));
        return new APIResponse<>(200, "Employee Found Successfully", employee);
    }

    //DELETE EMPLOYEE //CLEAR
    public APIResponse<EmployeeEntity> deleteEmployee(int id){
        EmployeeEntity employee = getEmployeeByIdEncrypted(id).getBody();

        employeeRepo.delete(employee);
        return new APIResponse<>(200, "Employee Deleted Successfully", decryptEmployee(employee));
    }

    //UPDATE EMPLOYEE //CLEAR
    public APIResponse<EmployeeEntity> updateEmployee(EmployeeEntity newEmployee, int id) {
        EmployeeEntity existingEmployee = getEmployeeByIdDecrypted(id).getBody();

        newEmployee.setId(id);

        newEmployee.hasDefault();

        newEmployee.setPayslips(existingEmployee.getPayslips());

        newEmployee.getAddress().setId(existingEmployee.getAddress().getId());

        newEmployee.setDepartment(departmentService.getDepartmentDecrypted(newEmployee.getDepartment().getId()).getBody());
        EmployeeEntity employee = employeeRepo.save(encryptEmployee(newEmployee));
        return new APIResponse<>(200, "Employee Updated Successfully", decryptEmployee(employee));
    }

    //ENCRYPT EMPLOYEE //CLEAR
    public EmployeeEntity encryptEmployee(EmployeeEntity employee){
        employee.setName(encryptionService.encrypt(employee.getName()));
        AddressEntity address = employee.getAddress();
        address.setPrimAddress(encryptionService.encrypt(address.getPrimAddress()));
        address.setCity(encryptionService.encrypt(address.getCity()));
        employee.setDepartment(departmentService.getDepartmentEncrypted(employee.getDepartment().getId()).getBody());
        employee.setAddress(address);
        return employee;
    }

    //DECRYPT EMPLOYEE //CLEAR
    public EmployeeEntity decryptEmployee(EmployeeEntity employee) {
        employee.setName(encryptionService.decrypt(employee.getName()));
        AddressEntity address = employee.getAddress();
        AddressEntity decryptedAddress = new AddressEntity(address.getId(),encryptionService.decrypt(address.getPrimAddress()),encryptionService.decrypt(address.getCity()),address.getPincode());
        employee.setAddress(decryptedAddress);
        DepartmentEntity department = employee.getDepartment();
        DepartmentEntity decryptedDepartment = new DepartmentEntity(department.getId(),encryptionService.decrypt(department.getName()));
        employee.setDepartment(decryptedDepartment);
        return employee;
    }
}