package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@Path("/")
public class RootResource{
    @GET
    public Response getRoot() {
        // Implementierung
        return Response.ok("Root of DVD-Rental Film. Returns array of primary resources").build();
    }
}
