package EmployeePackage.Controllers;

import EmployeePackage.Entities.PayslipEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class PayslipController {

    APIResponse apiResponse;

    @Autowired
    PayslipService payslipService;

    @PostMapping("/employee/{id}/payslip")
    ResponseEntity postPayslip(@RequestBody PayslipEntity payslipEntity, @PathVariable int id){
        if(payslipEntity.hasDefault()){
            apiResponse = new APIResponse<>(200.1,"Full Details Not Provided", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        PayslipEntity payslip = payslipService.postPayslip(payslipEntity,id);
        if(payslip == null){
            apiResponse = new APIResponse<>(200.1,"Employee Doesn't Exist", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        if(payslip.getMonth() == null){
            apiResponse = new APIResponse<>(200.1,"Payslip For This Month Already Exists", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        apiResponse = new APIResponse<>(200,"Payslip Created Successfully", payslip);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/employee/{id}/payslip")
    ResponseEntity getPayslip(@PathVariable int id){
        List<PayslipEntity> payslip = payslipService.getPayslip(id);
        payslip.forEach(item-> item.getEmployee().setPartialNull());
        if(payslip.isEmpty()){
            apiResponse = new APIResponse<>(200.1,"No payslip found", payslip);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        apiResponse = new APIResponse<>(200,"Payslip Retrieved Successfully", payslip);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }
}
