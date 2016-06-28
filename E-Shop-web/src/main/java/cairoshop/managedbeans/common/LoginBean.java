package cairoshop.managedbeans.common;

import cairoshop.entities.*;
import cairoshop.service.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.application.*;
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
public class LoginBean
{

    @EJB
    private UserService userService;

    private String email;
    private String password;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    /*
     1 -> is customer [welcome customer]
     2 -> is admin [welcome admin]        
     */
    public String login()
    {
        //null | "notFound" | instanceof User
        Object result = userService.signIn(email, password);
        FacesContext context = FacesContext.getCurrentInstance();

        if (result == null)
        {
            ViewHandler viewHandler = context.getApplication().getViewHandler();
            context.setViewRoot(viewHandler.createView(context, "/WEB-INF/utils/error.xhtml"));
            context.getPartialViewContext().setRenderAll(true);
            context.renderResponse();
        }
        else if (result instanceof String) // not registered yet || wrong userName or password
        {
            context.addMessage("login", new FacesMessage("Wrong email or password"));            
        }
        else if (result instanceof User) //true: active user
        {
            User user = (User) result;

            Map<String, Object> sessionMap = context                    
                                                .getExternalContext()
                                                .getSessionMap();
            
            sessionMap.put("currentUser", user);
            sessionMap.put("content", "/sections/initial-content.xhtml");

            if (user instanceof Admin)
            {
                return "admin";
            }

            return "customer";
        }

        return null;
    }

}
