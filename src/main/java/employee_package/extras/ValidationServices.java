package employee_package.extras;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationServices {

    public static void invalidCharCheck(String s){
        Pattern pattern = Pattern.compile("[#/@^*?]",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            throw new CustomException(400,"Invalid Characters Present");
        }
    }
}