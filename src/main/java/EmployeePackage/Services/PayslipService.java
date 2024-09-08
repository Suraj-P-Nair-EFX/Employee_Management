package EmployeePackage.Services;



import java.util.Collections;

import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Entities.PayslipEntity;
import EmployeePackage.Repositories.PayslipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayslipService {
    @Autowired
    PayslipRepo payslipRepo;

    @Autowired
    EmployeeService employeeService;

    public PayslipEntity postPayslip(PayslipEntity payslipEntity, int id){
        payslipEntity.setEmployee((EmployeeEntity) employeeService.getEmployeeById(id).getBody());
        if(!employeeService.isExistById(id)){
            return null;
        }

        List<PayslipEntity> payslip = getPayslip(id);
        if(!payslip.isEmpty()) {
            payslip.forEach(item -> {
                if (item.getMonth().equals(payslipEntity.getMonth()) && item.getEmployee() == payslipEntity.getEmployee()) {
                    payslipEntity.setMonth(null);
                }
            });
            if(payslipEntity.getMonth() == null){
                return payslipEntity;
            }
        }

        payslipEntity.setEmployee((EmployeeEntity)employeeService.getEmployeeById(id).getBody());
        return payslipRepo.save(payslipEntity);
    }

    public List<PayslipEntity> getPayslip(int id){
        if(employeeService.isExistById(id)) {
            List<PayslipEntity> payslips = payslipRepo.findByemployee((EmployeeEntity)employeeService.getEmployeeById(id).getBody());

            return payslips;
        }
        else{
            return Collections.emptyList();
        }
    }
}
