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
public class NameValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent uic, Object o) throws ValidatorException {
        if (!Pattern.compile("^([A-Za-z]{4,10})([ ]?([A-Za-z]{4,10}))*$").matcher((String) o).matches()) {
            throw new ValidatorException(new FacesMessage("Name contains letters only"));
        }
    }

}
