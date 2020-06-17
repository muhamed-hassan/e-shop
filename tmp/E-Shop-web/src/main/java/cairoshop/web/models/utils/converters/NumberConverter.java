package cairoshop.web.models.utils.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@FacesConverter(value = "numberConverter")
public class NumberConverter implements Converter {

    // from presentation to model
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    // from model to presentation
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
