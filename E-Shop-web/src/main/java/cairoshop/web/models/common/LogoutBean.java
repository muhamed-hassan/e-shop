package cairoshop.web.models.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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
