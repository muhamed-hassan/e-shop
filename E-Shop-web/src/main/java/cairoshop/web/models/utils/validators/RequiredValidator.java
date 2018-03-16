package cairoshop.web.models.utils.validators;

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
    public void validate(FacesContext fc, UIComponent uic, Object o) 
            throws ValidatorException {
        
        if (o == null || ((String) o).isEmpty()) {
            String componentId = uic.getId();
            switch (componentId) {
                case "email":
                    throw new ValidatorException(new FacesMessage("Email is required."));
                case "password":
                    throw new ValidatorException(new FacesMessage("Password is required."));
                case "category":
                    throw new ValidatorException(new FacesMessage("Category name is required."));
            }
        }
        
    }

}
