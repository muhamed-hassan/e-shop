package cairoshop.web.controllers.common;

import cairoshop.entities.*;
import cairoshop.service.*;
import cairoshop.utils.PasswordEncryptor;
import cairoshop.utils.SharedContent;
import java.util.*;
import javax.ejb.*;
import javax.faces.application.*;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.inject.Inject;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@RequestScoped
public class LoginBean {

    @EJB
    private UserService userService;

    private String email;
    private String password;
    
    @Inject
    private PasswordEncryptor encryptor;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     1 -> is customer [welcome customer]
     2 -> is admin [welcome admin]        
     */
    public String login() {
        //null | "notFound" | instanceof User
        Object result = userService.signIn(email, encryptor.encrypt(password));
        FacesContext context = FacesContext.getCurrentInstance();

        if (result == null) {
            ViewHandler viewHandler = context.getApplication().getViewHandler();
            context.setViewRoot(viewHandler.createView(context, SharedContent.ERROR));
            context.getPartialViewContext().setRenderAll(true);
            context.renderResponse();
        } else if (result instanceof String) { // not registered yet || wrong userName or password
            context.addMessage("login", new FacesMessage("Wrong email or password"));
        } else if (result instanceof User) { //true: active user
            User user = (User) result;

            Map<String, Object> sessionMap = context
                    .getExternalContext()
                    .getSessionMap();

            
            sessionMap.put("content", SharedContent.INITIAL_CONTEXT);

            if (user instanceof Admin) {
                sessionMap.put("currentUser", (Admin) user);
                return "admin";
            }

            sessionMap.put("currentUser", (Customer) user);
            return "customer";
        }

        return null;
    }

}
