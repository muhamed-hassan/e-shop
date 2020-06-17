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
public class AddressValidator implements Validator {
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {        
        if (!Patterns.ADDRESS_PATTERN.matcher((String) o).matches()) {
            throw new ValidatorException(new FacesMessage("Address should contain letter, numbers, and dots"));
        }        
    }

}
