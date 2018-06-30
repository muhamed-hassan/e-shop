package cairoshop.web.models.common;

import cairoshop.entities.*;
import cairoshop.services.interfaces.UserService;
import cairoshop.utils.*;
import java.util.*;
import javax.annotation.PostConstruct;
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
public class RegisterBean {

    @EJB
    private UserService userService;

    @Inject
    private PasswordEncryptor encryptor;
    
    private Customer customer;
    private ContactDetails contactDetails;

    @PostConstruct
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
        customer.setPassword(encryptor.encrypt(customer.getPassword()));
        User result = userService.signUp(customer);

        FacesContext context = FacesContext.getCurrentInstance();

        if (result == null) { // error in sign up - please try again later
            context.addMessage("register", new FacesMessage("Something went wrong - try again later"));
            return null;
        }

        Map<String, Object> sessionMap = context
                .getExternalContext()
                .getSessionMap();

        sessionMap.put("currentUser", (Customer) result);
        sessionMap.put("content", SharedContent.INITIAL_CONTEXT);

        return "done";
    }

}
