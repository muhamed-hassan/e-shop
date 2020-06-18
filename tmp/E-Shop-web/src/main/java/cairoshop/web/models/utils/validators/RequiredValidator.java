package cairoshop.web.models.utils.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
* LinkedIn    : https://www.linkedin.com/in/muhamed-hassan/                *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@FacesValidator
public class RequiredValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {        
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
