package cairoshop.web.endpoints;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

import cairoshop.repositories.interfaces.ProductRepository;
import cairoshop.web.endpoints.utils.MediaType;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Path("/images")
public class ImageResource {
    
    @Inject
    private ProductRepository productRepository;

    @Context
    private ServletContext servletContext;
    
    @GET
    @Path("/{id}")
    @Produces(
            {
                MediaType.IMAGE_JPEG, MediaType.IMAGE_JPG, MediaType.IMAGE_PNG
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
                
            } catch (Exception e) { }
        }

        return Response
                .ok(img)
                .build();
    }
}
