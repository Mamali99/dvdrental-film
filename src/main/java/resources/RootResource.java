package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/")
public class RootResource {
    @GET
    public Response getRoot() {
        // Implementierung
        return Response.ok("Root of DVD-Rental Film").build();
    }
}
