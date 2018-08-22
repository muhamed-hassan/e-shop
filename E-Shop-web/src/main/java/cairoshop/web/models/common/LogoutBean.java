package cairoshop.web.models.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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
