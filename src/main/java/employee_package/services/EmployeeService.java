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
        System.out.println("1");

        employeeEntity.getAddress().setId(employeeEntity.getId());
        System.out.println("2");

        employeeEntity.setDepartment(apiResponse.getBody());
        System.out.println("3");

        employeeEntity.hasDefault();


        invalidCharCheck(employeeEntity.toString());


        if(employeeRepo.existsById(employeeEntity.getId())) throw new CustomException(400,"Employee Already Exists");


        return new APIResponse<>(200,"Employee Created Successfully",decryptEmployee(employeeRepo.save(encryptEmployee(employeeEntity))));
    }

    //GET ALL EMPLOYEES //CLEAR
    public APIResponse<List<EmployeeEntity>> getAllEmployeeService(int page, int size){
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

        System.out.println("encrypt 1");
        employee.setName(encryptionService.encrypt(employee.getName()));
        System.out.println("encrypt 2");
        AddressEntity address = employee.getAddress();
        System.out.println("encrypt 3");
        address.setPrimAddress(encryptionService.encrypt(address.getPrimAddress()));
        System.out.println("encrypt 4");
        address.setCity(encryptionService.encrypt(address.getCity()));
        System.out.println("encrypt 5");
        employee.setDepartment(departmentService.getDepartmentEncrypted(employee.getDepartment().getId()).getBody());
        System.out.println("encrypt 6");
        employee.setAddress(address);
        System.out.println("encrypt finish");
        return employee;
    }

    //DECRYPT EMPLOYEE //CLEAR
    public EmployeeEntity decryptEmployee(EmployeeEntity employee) {

        System.out.println("decrypt 1");
        employee.setName(encryptionService.decrypt(employee.getName()));
        System.out.println("decrypt 2");
        AddressEntity address = employee.getAddress();
        System.out.println("decrypt 3");
        AddressEntity decryptedAddress = new AddressEntity(address.getId(),encryptionService.decrypt(address.getPrimAddress()),encryptionService.decrypt(address.getCity()),address.getPincode());
        System.out.println("decrypt 4");
        employee.setAddress(decryptedAddress);
        System.out.println("decrypt 5");
        DepartmentEntity department = employee.getDepartment();
        System.out.println("decrypt 6");
        DepartmentEntity decryptedDepartment = new DepartmentEntity(department.getId(),encryptionService.decrypt(department.getName()));
        System.out.println("decrypt 7");
        employee.setDepartment(decryptedDepartment);
        System.out.println("decrypt finish  ");
        return employee;
    }




}