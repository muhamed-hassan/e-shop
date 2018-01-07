package cairoshop.web.controllers.utils.validators;

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
public class MobileValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (!Pattern.compile(
                "^(01)\\d{9}$")
                .matcher((String) o).matches()) {
            throw new ValidatorException(new FacesMessage("Mobile number should contain 11 digits starting with \"01\""));
        }
    }

}
