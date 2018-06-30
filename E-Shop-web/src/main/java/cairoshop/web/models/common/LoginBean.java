package cairoshop.web.models.common;

import cairoshop.entities.*;
import cairoshop.services.interfaces.UserService;
import cairoshop.utils.*;
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

    @Inject
    private PasswordEncryptor encryptor;

    private String email;
    private String password;

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
        User user = userService.signIn(email, encryptor.encrypt(password));
        FacesContext context = FacesContext.getCurrentInstance();

        if (user == null) { // not registered yet || wrong userName or password
            context.addMessage("login", new FacesMessage("Wrong email or password"));
            return null;
        }

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

}
