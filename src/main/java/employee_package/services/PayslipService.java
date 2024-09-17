package employee_package.services;
import java.util.*;

import employee_package.entities.EmployeeEntity;
import employee_package.entities.PayslipEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.repositories.PayslipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayslipService {
    @Autowired PayslipRepo payslipRepo;

    @Autowired EmployeeService employeeService;
    String payslipNonExistent = "Payslip Doesn't Exist";

    //CREATE PAYSLIP //CLEAR
    public APIResponse<PayslipEntity> postPayslip(PayslipEntity payslipEntity, int id) {
        payslipEntity.hasDefault();

        Set<String> validMonths = new HashSet<>(Arrays.asList(
                "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        ));

        String month = payslipEntity.getMonth().toUpperCase();

        if (!validMonths.contains(month)) {
            throw new CustomException(400, "Invalid month: " + payslipEntity.getMonth());
        }

        payslipEntity.setMonth(month);
        payslipEntity.setEmployee(employeeService.getEmployeeByIdEncrypted(id).getBody());

        List<PayslipEntity> payslips = getPayslipEncrypted(id).getBody();
        if (payslips.stream().anyMatch(item -> item.equals(payslipEntity))) {
            throw new CustomException(200, "Payslip already exists");
        }

        return new APIResponse<>(200, "Payslip Created Successfully", decryptPayslip(payslipRepo.save(payslipEntity)));
    }

    //GET ENCRYPTED PAYSLIPS OF ONE EMPLOYEE //CLEAR
    public APIResponse<List<PayslipEntity>> getPayslipEncrypted(int id){
        List<PayslipEntity> payslips = payslipRepo.findByEmployee(employeeService.getEmployeeByIdEncrypted(id).getBody());
        if(payslips.isEmpty()) return new APIResponse<>(200.1, payslipNonExistent, Collections.emptyList());
        return new APIResponse<>(200,"Payslips Retrieved Successfully", payslips);
    }

    //GET DECRYPTED PAYSLIPS OF ONE EMPLOYEE //CLEAR
    public APIResponse<List<PayslipEntity>> getPayslipDecrypted(int id){
        List<PayslipEntity> payslips = payslipRepo.findByEmployee(employeeService.getEmployeeByIdEncrypted(id).getBody());
        if(payslips.isEmpty()) return new APIResponse<>(200.1, payslipNonExistent, Collections.emptyList());

        List<PayslipEntity> decryptedPayslips = payslips.stream()
                .map(this::decryptPayslip)
                .toList();
        return new APIResponse<>(200,"Payslips Retrieved Successfully",decryptedPayslips);
    }

    //GET ONE MONTH PAYSLIP OF ONE EMPLOYEE //CLEAR
    public APIResponse<PayslipEntity> getPayslipDecrypted(int id,String month){
        PayslipEntity payslips = payslipRepo.findByEmployeeAndMonth(employeeService.getEmployeeByIdEncrypted(id).getBody(),month.toUpperCase());
        if(payslips == null) return new APIResponse<>(200.1, payslipNonExistent, null);
        return new APIResponse<>(200,"Payslip Retrieved Successfully",decryptPayslip(payslips));
    }

    //DELETE ONE MONTH PAYSLIP OF ONE EMPLOYEE //CLEAR
    public APIResponse<PayslipEntity> deletePayslip(int id, String month) {
        PayslipEntity payslip = payslipRepo.findByEmployeeAndMonth(employeeService.getEmployeeByIdEncrypted(id).getBody(),month.toUpperCase());
        if(payslip==null)
                throw new CustomException(404, payslipNonExistent);
        payslipRepo.delete(payslip);
        return new APIResponse<>(200,"Payslip deleted Successfully", decryptPayslip(payslip));
    }

    public PayslipEntity decryptPayslip(PayslipEntity payslip){

        EmployeeEntity employee = payslip.getEmployee();
        EmployeeEntity decryptedEmployee = new EmployeeEntity(employee);
        payslip.setEmployee(employeeService.decryptEmployee(decryptedEmployee));
        return payslip;
    }
}