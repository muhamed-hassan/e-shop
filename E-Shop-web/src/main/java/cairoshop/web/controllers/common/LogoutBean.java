package cairoshop.web.controllers.common;

import javax.faces.bean.*;
import javax.faces.context.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@RequestScoped
public class LogoutBean {

    public String logout() {
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .clear();
        
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .invalidateSession();        

        return "logout";
    }

}
