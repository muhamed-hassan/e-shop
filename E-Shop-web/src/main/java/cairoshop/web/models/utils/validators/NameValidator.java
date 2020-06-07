package cairoshop.web.models.utils.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.util.regex.Pattern;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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
