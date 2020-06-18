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
                    if (!Patterns.PRICE_PATTERN.matcher(o.toString()).matches()) {
                        throw new ValidatorException(new FacesMessage(""));
                    }
                    break;
                }
                case "quantityInput": {
                    if (!Patterns.QUANTITY_PATTERN.matcher(o.toString()).matches()) {
                        throw new ValidatorException(new FacesMessage(""));
                    }
                    break;
                }
                case "pNameInput": {
                    if (!Patterns.PRODUCT_NAME_PATTERN.matcher(o.toString()).matches()) {
                        throw new ValidatorException(new FacesMessage(""));
                    }
                    break;
                }
                default: {
                    if (!Patterns.DEFAULT_PATTERN.matcher(o.toString()).matches()) {
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
