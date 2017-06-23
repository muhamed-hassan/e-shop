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
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (!Pattern.compile(
                "^[\\w\\.]+(@)[A-Za-z]+\\.([A-Za-z]{2,3})$")
                .matcher((String) o).matches()) {
            throw new ValidatorException(new FacesMessage("Invalid email format"));
        }
    }

}
