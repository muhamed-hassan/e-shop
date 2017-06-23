package cairoshop.managedbeans.common;

import cairoshop.entities.*;
import cairoshop.service.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.application.*;
import javax.faces.bean.*;
import javax.faces.context.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@RequestScoped
public class RegisterBean {

    @EJB
    private UserService userService;

    private Customer customer;
    private ContactDetails contactDetails;

    @javax.annotation.PostConstruct
    public void init() {
        customer = new Customer();
        contactDetails = new ContactDetails();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String register() {
        customer.setContactDetails(contactDetails);
        Object result = userService.signUp(customer);

        FacesContext context = FacesContext.getCurrentInstance();

        if (result == null) // error in sign up - please try again later
        {
            context.addMessage("register", new FacesMessage("Something went wrong - try again later"));
            return null;
        } else if (result instanceof String) {
            context.addMessage("register", new FacesMessage((String) result));
            return null;
        }

        Map<String, Object> sessionMap = context
                .getExternalContext()
                .getSessionMap();

        sessionMap.put("currentUser", (Customer) result);
        sessionMap.put("content", "/sections/initial-content.xhtml");

        return "done";
    }

}
