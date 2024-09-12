package EmployeePackage.Controllers;

import EmployeePackage.Entities.PayslipEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/employee/{id}/payslip")
    ResponseEntity getPayslip(@PathVariable int id){

        apiResponse = payslipService.getPayslip(id);
        if(apiResponse.getErrorCode()==200) return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        //Setting Unwanted Attributes To Null
//        List<PayslipEntity> payslip = (List<PayslipEntity>) apiResponse.getBody();
//        payslip.forEach(item-> item.getEmployee().setPartialNull());


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);

    }

    @DeleteMapping("/employee/{id}/payslip/{month}")
    ResponseEntity deletePayslip(@PathVariable int id, @PathVariable String month){
        return ResponseEntity.status(HttpStatus.OK).body(payslipService.deletePayslip(id,month));
    }
}
