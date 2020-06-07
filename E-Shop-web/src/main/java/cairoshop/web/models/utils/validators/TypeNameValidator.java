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
public class TypeNameValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String componentId = uic.getId();

        if (o == null || ((String) o).isEmpty()) {
            switch (componentId) {
                case "category":
                    throw new ValidatorException(new FacesMessage("Category name is required."));
                case "vendor":
                    throw new ValidatorException(new FacesMessage("Vendor name is required."));
            }
        } else {
            switch (componentId) {
                case "priceInput": {
                    if (!Pattern.compile("[1-9][0-9]{1,3}(\\.[0-9]*)?").matcher(o.toString()).matches()) {
                        throw new ValidatorException(new FacesMessage(""));
                    }
                    break;
                }
                case "quantityInput": {
                    if (!Pattern.compile("^[1-9][0-9]*$").matcher(o.toString()).matches()) {
                        throw new ValidatorException(new FacesMessage(""));
                    }
                    break;
                }
                case "pNameInput": {
                    if (!Pattern.compile("^([A-Za-z0-9\\.])([ ]?[A-Za-z0-9\\.])*$").matcher(o.toString()).matches()) {
                        throw new ValidatorException(new FacesMessage(""));
                    }
                    break;
                }
                default: {
                    if (!Pattern.compile("^[A-Za-z]{2,50}$").matcher(o.toString()).matches()) {
                        if (componentId.equals("category") || componentId.equals("vendor")) {
                            throw new ValidatorException(new FacesMessage("Letters only allowed"));
                        }
                        throw new ValidatorException(new FacesMessage(""));
                    }
                }
            }
        }
    }

}
