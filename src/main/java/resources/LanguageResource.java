package resources;

import entities.Film;
import entities.Language;
import entities.LanguageDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.LanguageService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@Path("/languages")
public class LanguageResource {

    @Inject
    LanguageService languageService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLanguages() {
        List<LanguageDTO> languages = languageService.getLanguages();
        return Response.ok(languages).build();
    }


}

