package cairoshop.web.endpoints;

import cairoshop.daos.*;
import com.cairoshop.logger.GlobalLogger;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.ManagedBean;
import javax.inject.*;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Path("/images")
@ManagedBean
public class ImageResource {

    @Inject
    private ProductDAO productDAO;

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
            
            img = productDAO.getImage(Integer.parseInt(id));
                        
            if ( img == null ) {
                throw new RuntimeException("The default image will be loaded");
            }

        } catch (Exception ex) {
            GlobalLogger
                    .getInstance()
                    .doLogging(
                            Level.ERROR, 
                            "getImage failed" + " | " + ImageResource.class.getName() + "::getImage( )", 
                            ex,
                            this.getClass());

            java.nio.file.Path errPath = Paths.get(servletContext.getRealPath("/resources/img/empty.jpg"));

            try {
                
                img = Files.readAllBytes(errPath);
                
            } catch (Exception e) {
                GlobalLogger
                        .getInstance()
                        .doLogging(
                                Level.ERROR, 
                                "Error occured during reading default image" + " | " + ImageResource.class.getName() + "::getImage( )", 
                                ex,
                                this.getClass());
            }
        }

        return Response
                .ok(img)
                .build();
    }
}
