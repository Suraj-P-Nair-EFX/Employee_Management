package employee_package.controllers;

import employee_package.entities.PayslipEntity;
import employee_package.extras.APIResponse;
import employee_package.services.PayslipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PayslipController {

    private static final Logger logger = LoggerFactory.getLogger(PayslipController.class);

    @Autowired
    PayslipService payslipService;

    @PostMapping("/employee/{id}/payslip") // CREATE PAYSLIP
    ResponseEntity<APIResponse<PayslipEntity>> postPayslip(@RequestBody PayslipEntity payslipEntity, @PathVariable int id) {
        logger.info("Received request to create payslip for employee ID: {}", id);
        APIResponse<PayslipEntity> apiResponse = payslipService.postPayslip(payslipEntity, id);
        logger.info("Payslip created successfully for employee ID: {}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/employee/{id}/payslip") // GET ALL PAYSLIP OF ONE EMPLOYEE
    ResponseEntity<APIResponse<List<PayslipEntity>>> getPayslip(@PathVariable int id) {
        logger.info("Received request to get all payslips for employee ID: {}", id);
        APIResponse<List<PayslipEntity>> apiResponse = payslipService.getPayslipDecrypted(id);
        logger.info("Successfully retrieved payslips for employee ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/employee/{id}/payslip/{month}") // GET ONE MONTH PAYSLIP OF ONE EMPLOYEE
    ResponseEntity<APIResponse<PayslipEntity>> getPayslip(@PathVariable int id, @PathVariable String month) {
        logger.info("Received request to get payslip for employee ID: {} for month: {}", id, month);
        APIResponse<PayslipEntity> apiResponse = payslipService.getPayslipDecrypted(id, month);
        logger.info("Successfully retrieved payslip for employee ID: {} for month: {}", id, month);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/employee/{id}/payslip/{month}") // DELETE ONE MONTH PAYSLIP OF ONE EMPLOYEE
    ResponseEntity<APIResponse<PayslipEntity>> deletePayslip(@PathVariable int id, @PathVariable String month) {
        logger.info("Received request to delete payslip for employee ID: {} for month: {}", id, month);
        APIResponse<PayslipEntity> apiResponse = payslipService.deletePayslip(id, month);
        logger.info("Successfully deleted payslip for employee ID: {} for month: {}", id, month);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
