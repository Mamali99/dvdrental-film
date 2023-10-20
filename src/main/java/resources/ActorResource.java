package resources;

import entities.Actor;
import entities.ActorDTO;
import entities.Film;
import entities.FilmsHref;
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
    public Response getActors(@QueryParam("page") @DefaultValue("1") int page) {
        List<Actor> actors = actorService.getFirst10Actors();

        List<ActorDTO> actorDTOs = new ArrayList<>();

        for (Actor actor : actors) {
            ActorDTO actorDTO = new ActorDTO();
            actorDTO.setId(actor.getActor_id());
            actorDTO.setFirstName(actor.getFirst_name());
            actorDTO.setLastName(actor.getLast_name());
            actorDTO.setFilms(new FilmsHref("/actors/" + actor.getActor_id() + "/films")); // Beispiel-URL

            actorDTOs.add(actorDTO);
        }

        return Response.ok(actorDTOs).build();
    }



    @POST
    public Response createActor(Actor actor) {
        // Implementierung
        return Response.created(URI.create("/actors/" + actor.getActor_id())).build();
    }

    @GET
    @Path("/count")
    public Response getActorCount() {
        Integer count = actorService.getActorCount();
        return Response.ok(count).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorById(@PathParam("id") int id) {
        Actor actor = actorService.getActorById(id);

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getActor_id());
        actorDTO.setFirstName(actor.getFirst_name());
        actorDTO.setLastName(actor.getLast_name());
        actorDTO.setFilms(new FilmsHref("/actors/" + actor.getActor_id() + "/films")); // Beispiel-URL

        return Response.ok(actorDTO).build();
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilmsByActorId(@PathParam("id") int id) {
        List<Film> films = actorService.getFilmsByActorId(id);
        return Response.ok(films).build();
    }
}
