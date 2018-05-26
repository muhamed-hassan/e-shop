package cairoshop.web.endpoints;

import cairoshop.repositories.interfaces.ProductRepository;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.ManagedBean;
import javax.inject.*;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Path("/images")
@ManagedBean
public class ImageResource {
    
    @Inject
    private ProductRepository productRepository;

    @Context
    private ServletContext servletContext;

    @GET
    @Path("/{id}")
    @Produces(
            {
                "image/jpg", "image/jpeg", "image/png"
            })
    public Response getImage(@PathParam("id") String id) {

        byte[] img = null;

        try {
            
            img = productRepository.getImage(Integer.parseInt(id));
                        
            if ( img == null ) {
                throw new RuntimeException("The default image will be loaded");
            }

        } catch (Exception ex) {

            java.nio.file.Path errPath = Paths.get(servletContext.getRealPath("/resources/img/empty.jpg"));

            try {
                
                img = Files.readAllBytes(errPath);
                
            } catch (Exception e) {
            }
        }

        return Response
                .ok(img)
                .build();
    }
}
