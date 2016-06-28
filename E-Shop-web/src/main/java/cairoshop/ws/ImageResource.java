package cairoshop.ws;

import cairoshop.daos.*;
import java.util.regex.*;
import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Path("/images")
public class ImageResource
{

    @Inject
    private ProductDAO productDAO;

    @GET
    @Path("/{id}")
    @Produces(
            {
                "image/jpg", "image/jpeg", "image/png"
            })
    public Object getImage(@PathParam("id") String id)
    {
        if ((id == null) || (id.isEmpty()) || (!Pattern.compile(
                "^[1-9][0-9]*$")
                .matcher(id).matches()))
        {
            return Response.status(Status.BAD_REQUEST).build();
        }

        byte[] img = productDAO.getImage(Integer.parseInt(id));

        return Response.ok(img).build();
    }
}
