package EmployeePackage.Services;
import java.util.Collections;
import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Entities.PayslipEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Repositories.PayslipRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PayslipService {
    @Autowired PayslipRepo payslipRepo;

    @Autowired EmployeeService employeeService;

    public APIResponse postPayslip(PayslipEntity payslipEntity, int id){
        if(payslipEntity.hasDefault()) return new APIResponse<>(200.1,"Full Details Not Provided", null);
        if(!employeeService.isExistById(id)) return new APIResponse<>(200.1,"Employee Doesn't Exist", null);

        payslipEntity.setEmployee((EmployeeEntity) employeeService.getEmployeeById(id).getBody());
        List<PayslipEntity> payslip = (List<PayslipEntity>)getPayslip(id).getBody();

        if(!payslip.isEmpty()) {
            payslip.forEach(item -> { if (item.equals(payslipEntity)) {payslipEntity.setMonth(null);}});
            if(payslipEntity.getMonth() == null) return new APIResponse<>(200.1,"Payslip Already Exists Exist", null);
        }
        payslipEntity.setEmployee((EmployeeEntity)employeeService.getEmployeeById(id).getBody());
        return new APIResponse<>(200,"Payslip Created Successfully", payslipRepo.save(payslipEntity));
    }

    public APIResponse getPayslip(int id){
        if(!employeeService.isExistById(id)) return new APIResponse<>(200.1,"Payslip Doesn't Exist", Collections.EMPTY_LIST);
        return new APIResponse<>(200,"Payslip Retrieved Successfully", payslipRepo.findByemployee((EmployeeEntity) employeeService.getEmployeeById(id).getBody()));
    }

    public void deleteAllPayslip(EmployeeEntity employee){
        List<PayslipEntity> payslips = payslipRepo.findByemployee(employee);
        if(!payslips.isEmpty()){
            payslips.forEach(item-> payslipRepo.delete(item));
        }
    }

    //NOT IMPLEMENTED
    //NEED TO IMPLEMENT ENCRYPTION
    public PayslipEntity deletePayslip(int id, String month) {
        PayslipEntity payslip = payslipRepo.findByEmployeeAndMonth((EmployeeEntity) employeeService.getEmployeeById(id).getBody(), month);
        return payslip;
    }


}