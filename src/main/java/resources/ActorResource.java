package resources;

import entities.Actor;
import entities.Film;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.ActorService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/actors")
public class ActorResource {

    @Inject
    ActorService actorService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Actor> getFirst10Actors() {
        return actorService.getFirst10Actors();
    }

/*
    @GET
    public List<Actor> getActors(@QueryParam("page") @DefaultValue("1") int page) {
        // Implementierung
        return new ArrayList<>();
    }

 */

    @POST
    public Response createActor(Actor actor) {
        // Implementierung
        return Response.created(URI.create("/actors/" + actor.getActor_id())).build();
    }

    @GET
    @Path("/count")
    public int getActorCount() {
        // Implementierung
        return 0;
    }

    @GET
    @Path("/{id}")
    public Actor getActorById(@PathParam("id") int id) {
        // Implementierung
        return new Actor();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteActor(@PathParam("id") int id) {
        // Implementierung
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateActor(@PathParam("id") int id, List<String> values) {
        // Implementierung
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/films")
    public List<Film> getFilmsByActorId(@PathParam("id") int id) {
        // Implementierung
        return new ArrayList<>();
    }
}
