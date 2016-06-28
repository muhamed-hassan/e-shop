package cairoshop.service;

import cairoshop.entities.*;
import javax.ejb.embeddable.*;
import org.junit.*;
import static org.junit.Assert.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class UserServiceTest
{
    
    private EJBContainer container;
    private UserService userService;
    
    @Before
    public void init() throws Exception
    {
        container = EJBContainer.createEJBContainer();
        userService = (UserService)container.getContext().lookup("java:global/classes/UserService");
    }
    
    @After
    public void clean()
    {
        container.close();        
    }
    
    public UserServiceTest()
    {
    }

    /**
     * Test of signIn method, of class UserService.
     */
    @Test
    public void testSignIn()
    {
        String email = "admin@local.com";
        String password = "admin";
        Object result = userService.signIn(email, password);
        assertNotNull(result);
    }

    /**
     * Test of signUp method, of class UserService.
     */
    @Test
    public void testSignUp() 
    {
        Customer customer = new Customer();
        customer.setMail("ahmed_9090@live.com");
        customer.setName("ahmed samir");
        customer.setPassword("22222");
        customer.setUserName("ahmedsamir");
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setAddress("cairo, egypt");
        contactDetails.setPhone("03222343244");
        customer.setContactDetails(contactDetails);
        
        Object result = userService.signUp(customer);        
        assertTrue(result instanceof Customer);
    }
    
}
