package EmployeePackage.Extras;
import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Services.EmployeeService;
import EmployeePackage.Services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationServices {

    public void invalidCharCheck(String s){
        Pattern pattern = Pattern.compile("[#/@^*?]",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            throw new CustomException(200.1,"Invalid Characters Present");
        }
    }


}