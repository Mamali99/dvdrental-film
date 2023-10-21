package resources;

import entities.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.ActorService;
import services.FilmService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/actors")
public class ActorResource {

    @Inject
    ActorService actorService;

    @Inject
    FilmService filmService;


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

            List<FilmsHref> filmsLinks = new ArrayList<>();
            for (Film film : actor.getFilms()) {
                filmsLinks.add(new FilmsHref("/films/" + film.getFilm_id()));
            }
            actorDTO.setFilms(filmsLinks);

            actorDTOs.add(actorDTO);
        }

        return Response.ok(actorDTOs).build();
    }




    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createActor(ActorDTO actorDTO) {
        Actor actor = new Actor();
        //actor.setFilms(new ArrayList<>());
        actor.setFirst_name(actorDTO.getFirstName());
        actor.setLast_name(actorDTO.getLastName());

        // Beziehung zu Film basierend auf den href-Werten hinzufügen
        if (actorDTO.getFilms() != null && !actorDTO.getFilms().isEmpty()) {
            for (FilmsHref filmHref : actorDTO.getFilms()) {
                if (filmHref.getHref() != null) {
                    String filmIdStr = filmHref.getHref().replaceAll("[^0-9]", "");
                    Integer filmId = Integer.parseInt(filmIdStr);
                    Film film = filmService.getFilmById(filmId);
                    if (film != null) {
                        actor.getFilms().add(film);
                        film.getActors().add(actor);
                    }
                }
            }
        }

        actorService.createActor(actor);

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
        Actor actor = actorService.getActorById(id);

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getActor_id());
        actorDTO.setFirstName(actor.getFirst_name());
        actorDTO.setLastName(actor.getLast_name());

        List<FilmsHref> filmsLinks = new ArrayList<>();
        for (Film film : actor.getFilms()) {
            filmsLinks.add(new FilmsHref("/films/" + film.getFilm_id()));
        }
        actorDTO.setFilms(filmsLinks);

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
    public Response updateActor(@PathParam("id") int id, List<String> values) {
        // Implementierung
        return Response.noContent().build();
    }
    @GET
    @Path("/{id}/films")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilmsByActorId(@PathParam("id") int id) {
        Actor actor = actorService.getActorById(id);
        List<FilmDTO> filmDTOs = new ArrayList<>();

        for (Film film : actor.getFilms()) {
            FilmDTO filmDTO = new FilmDTO();
            filmDTO.setId(film.getFilm_id());
            filmDTO.setTitle(film.getTitle());
            filmDTO.setDescription(film.getDescription());
            filmDTO.setLength(film.getLength());
            filmDTO.setRating(film.getRating());
            filmDTO.setReleaseYear(film.getRelease_year());
            filmDTO.setRentalDuration(film.getRental_duration());
            filmDTO.setRentalRate(film.getRental_rate());
            filmDTO.setReplacementCost(film.getReplacement_cost());
            filmDTO.setLanguage(film.getLanguage().getName().trim());

            List<FilmsHref> actorsLinks = new ArrayList<>();
            for (Actor actorInFilm : film.getActors()) {
                actorsLinks.add(new FilmsHref("/actors/" + actorInFilm.getActor_id() + "/films"));
            }
            filmDTO.setActors(actorsLinks);

            List<String> categories = new ArrayList<>();
            for (Category category : film.getCategories()) {
                categories.add(category.getName());
            }
            filmDTO.setCategories(categories);

            filmDTOs.add(filmDTO);
        }

        return Response.ok(filmDTOs).build();
    }

    /*
    @GET
    @Path("/{id}/films")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilmsByActorId(@PathParam("id") int id) {
        Actor actor = actorService.getActorById(id);
        List<FilmDTO> filmDTOs = new ArrayList<>();

        List<FilmsHref> filmsLinks = new ArrayList<>();
        for (Film film : actor.getFilms()) {
            filmsLinks.add(new FilmsHref("/films/" + film.getFilm_id()));
        }


        return Response.ok(filmsLinks).build();
    }

     */

}
