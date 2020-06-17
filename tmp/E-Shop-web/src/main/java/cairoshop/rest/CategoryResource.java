package cairoshop.rest;

import cairoshop.entities.Customer;
import cairoshop.entities.User;
import cairoshop.services.interfaces.CategoryService;
import cairoshop.utils.MediaType;
import com.cairoshop.dtos.NewCategoryDTO;
import com.cairoshop.dtos.NewCustomerDTO;
import com.cairoshop.dtos.SavedCategoryDTO;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/* **************************************************************************
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *
 * GitHub      : https://github.com/muhamed-hassan                          *
 * ************************************************************************ */
@Singleton
@Path("/categories")
public class CategoryResource {
    
    @EJB
    private CategoryService categoryService;
    
    //admin
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(NewCategoryDTO newCategoryDTO, @Context HttpServletRequest request) {
        int newlyCreatedCategoryId = categoryService.add(newCategoryDTO);        
        UriBuilder builder = UriBuilder.fromPath(request.getRequestURI())
                                        .path(CategoryResource.class, "getCategory");
        
        return Response.created(builder.build(newlyCreatedCategoryId)).build();
    }
    
    //admin
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") int id) {
        SavedCategoryDTO savedCategoryDTO = categoryService.getById(id);        
        return Response.ok(savedCategoryDTO).build();
    }
    
    //admin
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editCategory(@PathParam("id") int id, Map<String, Object> fields) {
        fields.put("id", id);
        categoryService.edit(fields);
        return Response.noContent().build();
    }
    
    //admin
    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") int id) {
        categoryService.remove(id);
        return Response.noContent().build();
    }
    
}
