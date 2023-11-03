package resources;

import dto.ActorDTO;
import dto.CategoryDTO;
import dto.FilmDTO;
import entities.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import services.FilmService;
import utils.UpdateRequestFilm;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/films")
public class FilmResource {

    @Inject
    private FilmService filmService;

    @Context
    private UriInfo uriInfo;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilms(@QueryParam("page") @DefaultValue("1") int page) {
        List<FilmDTO> filmDTOs = filmService.getFilmDTOs(page);
        return Response.ok(filmDTOs).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFilm(FilmDTO filmDTO) {
        Film film = filmService.createFilmFromDTO(filmDTO);
        if(film == null){
            return Response.serverError().entity("Fehler beim Erstellen des Films").build();
        }
        URI actorUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(film.getFilm_id())).build();
        return Response.created(actorUri).build();

    }



    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public int getFilmCount() {
        return filmService.getFilmCount();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getFilmById(@PathParam("id") int id) {
        FilmDTO filmDTO = filmService.getFilmDTOById(id);
        if (filmDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Film not found for ID: " + id).build();
        }
        return Response.ok(filmDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFilm(@PathParam("id") int id) {
        return null;
    }

    @PATCH
    @Path("/{id}")
    public Response updateFilm(@PathParam("id") int id, List<UpdateRequestFilm> values) {
        boolean isUpdate = filmService.updateFilm(id, values);

        if(!isUpdate) {
            return Response.status(Response.Status.NOT_FOUND).entity("Film not found or there is a problem with the update for ID: " + id).build();
        }
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/actors")
    public Response getActorsByFilmId(@PathParam("id") int id) {
        List<ActorDTO> actorDTOs = filmService.getActorsByFilmId(id);
        if (actorDTOs == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Film not found for ID: " + id).build();
        }
        return Response.ok(actorDTOs).build();
    }

    @PUT
    @Path("/{id}/actors/{actorId}")
    public Response addActorToFilm(@PathParam("id") int filmId, @PathParam("actorId") int actorId) {
        List<URI> listOfActors = filmService.addActorToFilm(filmId, actorId);
        if(listOfActors == null || listOfActors.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity("Film / actor not found for ID: " + filmId + " and " + actorId).build();
        }
        // Konvertiert die Liste von URIs zu einem String, der durch Kommas getrennt ist
        String actorLocations = String.join(",", listOfActors.stream().map(URI::toString).collect(Collectors.toList()));
        return Response.created(URI.create("/films/" + filmId + "/actors")).header("Location", actorLocations).build();

    }

    @GET
    @Path("/{id}/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoriesByFilmId(@PathParam("id") int id) {
        List<CategoryDTO> categoryDTOs = filmService.getCategoriesByFilmId(id);
        if (categoryDTOs == null || categoryDTOs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No categories found for Film ID: " + id).build();
        }
        return Response.ok(categoryDTOs).build();
    }


    @PUT
    @Path("/{id}/categories/{category}")
    public Response addCategoryToFilm(@PathParam("id") int filmId, @PathParam("category") int category) {
        filmService.addCategoryToFilm(filmId, category);
        return Response.created(URI.create("/films/" + filmId + "/categories")).build();
    }
}
