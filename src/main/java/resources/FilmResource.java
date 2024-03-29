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
import utils.InventoryServiceClient;
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

    @Inject
    private InventoryServiceClient inventoryServiceClient;


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
            return Response.serverError().entity("Error in creating the film.").build();
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

            boolean inventories = inventoryServiceClient.getInventoriesByFilmId(id);
            if(inventories){
                filmService.deleteFilm(id);
                return Response.noContent().build(); // Statuscode 204
            }else {
                System.out.println("The film cannot be deleted....");
                return Response.status(Response.Status.NOT_FOUND).build();
            }
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
        try {
            List<URI> listOfActors = filmService.addActorToFilm(filmId, actorId);
            // Konvertiert die Liste von URIs zu einem String, der durch Kommas getrennt ist
            String actorLocations = String.join(",", listOfActors.stream().map(URI::toString).collect(Collectors.toList()));
            return Response.created(URI.create("/films/" + filmId + "/actors")).header("Location", actorLocations).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
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
        try{
            List<URI> listOfCategories = filmService.addCategoryToFilm(filmId, category);
            String categoryLocations = String.join(",", listOfCategories.stream().map(URI::toString).collect(Collectors.toList()));
            return Response.created(URI.create("/films/" + filmId + "/categories")).header("Location", categoryLocations).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }
}
