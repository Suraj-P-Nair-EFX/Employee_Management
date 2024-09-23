package employee_package.services;

import java.time.LocalDate;
import java.util.*;
import employee_package.entities.EmployeeEntity;
import employee_package.entities.PayslipEntity;
import employee_package.extras.APIResponse;
import employee_package.extras.CustomException;
import employee_package.repositories.PayslipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PayslipService {
    @Autowired PayslipRepo payslipRepo;

    @Autowired EmployeeService employeeService;
    String payslipNonExistent = "Payslip Doesn't Exist";

    //CREATE PAYSLIP //CLEAR
    public APIResponse<PayslipEntity> postPayslip(PayslipEntity payslipEntity, int id) {
        payslipEntity.hasDefault();
        checkDate(payslipEntity.getDate());
        payslipEntity.setEmployee(employeeService.getEmployeeByIdEncrypted(id).getBody());
        List<PayslipEntity> payslips = getPayslipEncrypted(id).getBody();
        for (PayslipEntity item : payslips) {
            if(item.equals(payslipEntity)){
                throw new CustomException(400,"Payslip Already Exists");
            }
        }
        return new APIResponse<>(200, "Payslip Created Successfully", decryptPayslip(payslipRepo.save(payslipEntity)));
    }

    //GET ENCRYPTED PAYSLIPS OF ONE EMPLOYEE //CLEAR
    public APIResponse<List<PayslipEntity>> getPayslipEncrypted(int id){
        List<PayslipEntity> payslips = payslipRepo.findByEmployeeId(id);

        if(payslips.isEmpty()) return new APIResponse<>(200.1, payslipNonExistent, Collections.emptyList());
        return new APIResponse<>(200,"Payslips Retrieved Successfully", payslips);
    }

    //GET ALL PAYSLIP OF ONE EMPLOYEE AS PAGES //CLEAR
    public APIResponse<List<PayslipEntity>> getPayslipDecrypted(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PayslipEntity> payslipPage = payslipRepo.findAllByEmployeeId(id, pageable);
        if (payslipPage.isEmpty()) {
            throw new CustomException(404,"No Payslips Found");
        }
        List<PayslipEntity> decryptedPayslips = payslipPage.getContent().stream()
                .map(this::decryptPayslip)
                .toList();
        return new APIResponse<>(200, "Payslips Retrieved Successfully", decryptedPayslips);
    }

    //GET ONE MONTH PAYSLIP OF ONE EMPLOYEE //CLEAR
    public APIResponse<PayslipEntity> getPayslipDecrypted(int id, LocalDate date){
        checkDate(date);
        PayslipEntity payslips = payslipRepo.findByEmployeeAndDate(employeeService.getEmployeeByIdEncrypted(id).getBody(),date);
        if(payslips == null) return new APIResponse<>(200.1, payslipNonExistent, null);
        return new APIResponse<>(200,"Payslip Retrieved Successfully",decryptPayslip(payslips));
    }

    //DELETE ONE MONTH PAYSLIP OF ONE EMPLOYEE //CLEAR
    public APIResponse<PayslipEntity> deletePayslip(int id, LocalDate date) {
        checkDate(date);
        PayslipEntity payslip = payslipRepo.findByEmployeeAndDate(employeeService.getEmployeeByIdEncrypted(id).getBody(),date);
        if(payslip==null)
                throw new CustomException(404, payslipNonExistent);
        payslipRepo.delete(payslip);
        return new APIResponse<>(200,"Payslip deleted Successfully", decryptPayslip(payslip));
    }

    //GET PAYSLIPS OF ONE EMPLOYEE BETWEEN 2 DATES //CLEAR
    public APIResponse<List<PayslipEntity>> findPayslipBetweenMonths(int id, LocalDate from, LocalDate to, int page, int size) {
        checkDate(from);
        Pageable pageable = PageRequest.of(page, size);
        Page<PayslipEntity> payslipPage = payslipRepo.findByEmployeeAndDateBetween(
                employeeService.getEmployeeByIdEncrypted(id).getBody(), from, to, pageable);
        if (payslipPage.isEmpty())
            throw new CustomException(404, payslipNonExistent);

        List<PayslipEntity> decryptedPayslips = payslipPage.getContent().stream()
                .map(this::decryptPayslip)
                .toList();
        return new APIResponse<>(200, "Payslips found successfully", decryptedPayslips);
    }

    public PayslipEntity decryptPayslip(PayslipEntity payslip){

        EmployeeEntity employee = payslip.getEmployee();
        EmployeeEntity decryptedEmployee = new EmployeeEntity(employee);
        payslip.setEmployee(employeeService.decryptEmployee(decryptedEmployee));
        return payslip;
    }

    public void checkDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if(date.isAfter(currentDate))
            throw new CustomException(400.1,"Invalid Date");
    }
}