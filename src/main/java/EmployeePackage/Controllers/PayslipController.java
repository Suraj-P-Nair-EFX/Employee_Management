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
        apiResponse = payslipService.postPayslip(payslipEntity,id);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }


    @GetMapping("/employee/{id}/payslip")
    ResponseEntity getPayslip(@PathVariable int id){
        List<PayslipEntity> payslip = (List<PayslipEntity>)payslipService.getPayslip(id).getBody();
        payslip.forEach(item-> item.getEmployee().setPartialNull());
        if(payslip.isEmpty()){
            apiResponse = new APIResponse<>(200.1,"No payslip found", payslip);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
        apiResponse = new APIResponse<>(200,"Payslip Retrieved Successfully", payslip);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }
}
