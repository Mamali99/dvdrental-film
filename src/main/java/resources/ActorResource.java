package resources;

import dto.ActorDTO;
import dto.FilmDTO;
import entities.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.ActorService;
import utils.UpdateRequestActor;

import java.net.URI;
import java.util.List;

@Path("/actors")
public class ActorResource {

    @Inject
    ActorService actorService;



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActors(@QueryParam("page") @DefaultValue("1") int page) {
        List<ActorDTO> actorDTOs = actorService.getActorDTOs(page);
        return Response.ok(actorDTOs).build();
    }



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createActor(ActorDTO actorDTO) {
        Actor actor = actorService.createActorFromDTO(actorDTO);
        return Response.created(URI.create("/actors/" + actor.getActor_id())).build();
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorCount() {
        Integer count = actorService.getActorCount();
        return Response.ok(count.toString()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorById(@PathParam("id") int id) {
        ActorDTO actorDTO = actorService.getActorDTOById(id);

        if (actorDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(actorDTO).build();
    }



    @DELETE
    @Path("/{id}")
    public Response deleteActor(@PathParam("id") int id) {
        boolean isDeleted = actorService.deleteActor(id);

        if (!isDeleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Actor not found").build();
        }

        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateActor(@PathParam("id") int id, List<UpdateRequestActor> updates) {
        boolean updateSuccessful = actorService.updateActor(id, updates);

        if (updateSuccessful) {
            return Response.noContent().build(); // 204 No Content
        } else {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 Not Found
        }
    }
    @GET
    @Path("/{id}/films")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilmsByActorId(@PathParam("id") int id) {
        List<FilmDTO> filmDTOs = actorService.getFilmsByActorId(id);
        return Response.ok(filmDTOs).build();
    }



}
