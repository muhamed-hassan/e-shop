package cairoshop.validators;

import java.util.*;
import java.util.regex.*;
import javax.faces.application.*;
import javax.faces.component.*;
import javax.faces.context.*;
import javax.faces.validator.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@FacesValidator
public class TypeNameValidator implements Validator
{
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException
    {
        String componentId = uic.getId();
        
        if (o == null || ((String) o).isEmpty())
        {            
            if (componentId.equals("category"))
            {
                throw new ValidatorException(new FacesMessage("Category name is required."));
            }            
            else if (componentId.equals("vendor"))
            {
                throw new ValidatorException(new FacesMessage("Vendor name is required."));
            }
        }
        else
        {
            switch(componentId)
            {
                case "priceInput":
                {
                    boolean valid = Pattern.compile(
                            "^[1-9][0-9]{2,4}(\\.[0-9]*)?$")
                            .matcher(o.toString()).matches();
                    
                    if( !valid )
                        throw new ValidatorException(new FacesMessage(""));
                    
                    break;
                }
                case "quantityInput":
                {
                    boolean valid = Pattern.compile(
                            "^[1-9][0-9]*$")
                            .matcher(o.toString()).matches();
                    
                    if( !valid )
                        throw new ValidatorException(new FacesMessage(""));
                    
                    break;
                }
                case "pNameInput":
                {
                    boolean valid = Pattern.compile(
                            "^([A-Za-z0-9\\.])([ ]?[A-Za-z0-9\\.])*$")
                            .matcher(o.toString()).matches();
                    
                    if( !valid )
                        throw new ValidatorException(new FacesMessage(""));
                    
                    break;
                }                
                default:
                {
                    boolean valid = Pattern.compile("^[A-Za-z]{2,50}$").matcher(o.toString()).matches();
                    
                    if( !valid )
                    {
                        if(componentId.equals("category") || componentId.equals("vendor") )
                        {
                            throw new ValidatorException(new FacesMessage("Letters only allowed"));
                        }
                        
                        throw new ValidatorException(new FacesMessage(""));
                    }
                }                
            }            
        }
    }
    
}

