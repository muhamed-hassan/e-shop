package cairoshop.web.models.utils.validators;

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
public class AddressValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (!Pattern.compile(
                "^[\\w\\.,][\\s\\w\\.,]*$")
                .matcher((String) o).matches()) {
            throw new ValidatorException(new FacesMessage("Address should contain letter, numbers, and dots"));
        }
    }

}
