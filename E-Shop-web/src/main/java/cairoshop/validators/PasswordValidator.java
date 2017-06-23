package cairoshop.validators;

import java.util.regex.*;
import javax.faces.application.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.faces.validator.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@FacesValidator
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (!Pattern.compile(
                "^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].)(?=.*[a-z]).{8,}$")
                .matcher((String) o).matches()) {
            throw new ValidatorException(new FacesMessage(
                    "Password should contain at least 8 characters including 1 capital case letter, 1 small case letter, 1 digit, and 1 special character at least"));
        }
    }

}
