package cairoshop.web.models.utils.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@FacesValidator
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {        
        if (!Patterns.PASSWORD_PATTERN.matcher((String) o).matches()) {
            throw new ValidatorException(new FacesMessage("Password should contain at least 8 characters including 1 capital case letter, 1 small case letter, 1 digit, and 1 special character at least"));
        }        
    }

}
