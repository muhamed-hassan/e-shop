package cairoshop.managedbeans.common;

import javax.faces.bean.*;
import javax.faces.context.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@RequestScoped
public class LogoutBean
{
    
    public String logout()
    {
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("currentUser", null);
        
        return "logout";
    }
    
}
