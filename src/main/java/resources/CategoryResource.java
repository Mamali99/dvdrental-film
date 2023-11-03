package resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.CategoryService;


@Path("/categories")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    //List of categories textual form
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory() {

        return Response.ok(categoryService.getCategory()).build();
    }
}
