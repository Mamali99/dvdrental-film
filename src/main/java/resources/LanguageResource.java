package resources;

import entities.Film;
import entities.Language;
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
    public List<Language> getFirst10Language() {
        return languageService.get10Languages();
    }

    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLanguages() {
        List<String> languages = fetchLanguages(); // Implementierung der Methode, um die Sprachen abzurufen
        return Response.ok(languages).build();
    }

    private List<String> fetchLanguages() {
        // Implementierung, um die Liste der Sprachen abzurufen
        // Zum Beispiel: return Arrays.asList("English", "German", "French");
        return null;
    }

     */
}

