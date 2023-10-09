package resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/")
public class test {

    @GET
    @Path("/test")
    @Produces("text/plain")
    public String getString(){
        return "Hallo";
    }
}
