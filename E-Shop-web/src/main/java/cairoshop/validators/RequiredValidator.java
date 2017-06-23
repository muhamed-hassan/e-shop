package cairoshop.validators;

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
public class RequiredValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (o == null || ((String) o).isEmpty()) {
            String componentId = uic.getId();
            if (componentId.equals("email")) {
                throw new ValidatorException(new FacesMessage("Email is required."));
            } else if (componentId.equals("password")) {
                throw new ValidatorException(new FacesMessage("Password is required."));
            } else if (componentId.equals("category")) {
                throw new ValidatorException(new FacesMessage("Category name is required."));
            }
        }
    }

}
