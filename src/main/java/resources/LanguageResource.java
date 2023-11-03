package resources;

import dto.LanguageDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.LanguageService;

import java.util.List;
@Path("/languages")
public class LanguageResource {

    @Inject
    LanguageService languageService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLanguages() {
        List<String> languages = languageService.getLanguages();
        return Response.ok(languages).build();
    }


}

