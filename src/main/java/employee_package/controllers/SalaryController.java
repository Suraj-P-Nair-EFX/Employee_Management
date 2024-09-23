package employee_package.controllers;
import employee_package.entities.SalaryEntity;
import employee_package.extras.APIResponse;
import employee_package.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SalaryController {
    @Autowired SalaryService salaryService;

    //CREATE SALARY //CLEAR
    @PostMapping("employee/{id}/salary")
    public ResponseEntity<APIResponse<SalaryEntity>> createSalary(@RequestBody SalaryEntity salary, @PathVariable int id){
        APIResponse<SalaryEntity> apiResponse = salaryService.createSalary(salary,id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    //GET SINGLE SALARY //CLEAR
    @GetMapping("employee/{id}/salary")
    public ResponseEntity<APIResponse<SalaryEntity>> getSalaryOfOneEmployee(@PathVariable int id){
        APIResponse<SalaryEntity> apiResponse = salaryService.getSalaryDecrypt(id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    //GET ALL SALARIES //CLEAR
    @GetMapping("employee/allSalaries")
    public ResponseEntity<APIResponse<List<SalaryEntity>>> getAllSalaries() {
        APIResponse<List<SalaryEntity>> apiResponse = salaryService.getALlSalaries();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    //DELETE SALARY //CLEAR
    @DeleteMapping("employee/{id}/salary")
    public ResponseEntity<APIResponse<SalaryEntity>> deleteSalary(@PathVariable int id){
        APIResponse<SalaryEntity> apiResponse = salaryService.deleteSalary(id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    //UPDATE SALARY //CLEAR
    @PostMapping("update/salary/{id}")
    public ResponseEntity<APIResponse<SalaryEntity>> updateSalary(@RequestBody SalaryEntity salary, @PathVariable int id){
        APIResponse<SalaryEntity> apiResponse = salaryService.updateSalary(salary,id);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
