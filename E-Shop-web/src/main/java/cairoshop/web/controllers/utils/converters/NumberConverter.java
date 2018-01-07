package cairoshop.web.controllers.utils.converters;

import javax.faces.component.*;
import javax.faces.context.*;
import javax.faces.convert.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@FacesConverter(value = "numberConverter")
public class NumberConverter implements Converter {

    // from presentation to model
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value.toString();
    }

    // from model to presentation
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
