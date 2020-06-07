package cairoshop.web.models.common;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import java.util.Map;

import cairoshop.entities.ContactDetails;
import cairoshop.entities.Customer;
import cairoshop.entities.User;
import cairoshop.services.interfaces.UserService;
import cairoshop.utils.PasswordEncryptor;
import cairoshop.pages.SharedContent;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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

        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        sessionMap.put("currentUser", (Customer) result);
        sessionMap.put("content", SharedContent.INITIAL_CONTEXT);
        return "done";
    }

}
