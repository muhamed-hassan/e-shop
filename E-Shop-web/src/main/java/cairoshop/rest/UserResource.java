package cairoshop.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.cairoshop.dtos.NewCustomerDTO;
import com.cairoshop.dtos.NewCustomerStatusDTO;
import com.cairoshop.dtos.SavedCustomerDTO;
import com.cairoshop.dtos.SavedCustomersDTO;

import cairoshop.entities.Customer;
import cairoshop.entities.User;
import cairoshop.utils.MediaType;
import cairoshop.utils.PasswordEncryptor;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
@Path("/users")
public class UserResource {
    
    /*@EJB
    private AdminService adminService;*/
    
    /*@EJB
    private UserService userService;*/

    @Inject
    private PasswordEncryptor encryptor;

    // Not logged user
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUp(NewCustomerDTO newCustomerDTO, @Context HttpServletRequest request) {
        Customer customer = new Customer.Builder(newCustomerDTO.getName(), newCustomerDTO.getEmail())
                                            .active(true)
                                            .withUsername(encryptor.encrypt(newCustomerDTO.getUsername()))
                                            .withPassword(newCustomerDTO.getPassword())
                                            .withContactDetails(newCustomerDTO.getAddress(), newCustomerDTO.getPhone())
                                        .build();        
        
        User registeredCustomer = null;//userService.signUp(customer);
        
        UriBuilder builder = UriBuilder.fromPath(request.getRequestURI())
                                        .path(UserResource.class, "getUser");
        
        return Response.created(builder.build(registeredCustomer.getId())).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        //SavedCustomerDTO 
        
        return Response.ok(null).build();
    }
    
    /*
    SavedCustomersDTO
    */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@QueryParam("start-position") int startPosition) {
        List<User> customers = null;//adminService.getCustomers(startPosition);
        int allCustomersCount = 0;//adminService.getCustomersCount();

        SavedCustomersDTO savedCustomersDTO = new SavedCustomersDTO();
        for (User customer : customers) {
            SavedCustomerDTO savedCustomerDTO = new SavedCustomerDTO();
            savedCustomerDTO.setId(customer.getId());
            savedCustomerDTO.setName(customer.getName());
            savedCustomerDTO.setEmail(customer.getEmail());
            savedCustomerDTO.setActive(customer.getActive());
            savedCustomersDTO.getCustomers().add(savedCustomerDTO);
        }

        savedCustomersDTO.setAllCount(allCustomersCount);

        return Response.ok(savedCustomersDTO).build();
    }
    
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeUserState(@PathParam("id") int id, NewCustomerStatusDTO newCustomerStatus) {
        User user = new Customer();
        user.setId(id);
        user.setActive(newCustomerStatus.isActive());
        //adminService.changeUserState(user);
        return Response.noContent().build();
    }
        
}
