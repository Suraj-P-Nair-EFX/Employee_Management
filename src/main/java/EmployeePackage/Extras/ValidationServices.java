package EmployeePackage.Extras;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationServices {

    public boolean invalidCharCheck(String s){
        Pattern pattern = Pattern.compile("[#/@^*?]",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
}
