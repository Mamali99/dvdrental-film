package resources;

import entities.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.FilmService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/films")
public class FilmResource {

    @Inject
    FilmService filmService;

    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilms(@QueryParam("page") @DefaultValue("1") int page) {
        // Implementierung
        List<Film> films = filmService.getFirst20Films();
        List<FilmDTO> filmDTOs = new ArrayList<>();

        for (Film film : films) {
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
            filmDTO.setLanguage(film.getLanguage().getName().trim()); // Annahme, dass der Name des Sprachfelds verwendet wird
            List<FilmsHref> actorsLinks = new ArrayList<>();
            for (Actor actor : film.getActors()) {
                actorsLinks.add(new FilmsHref("/actors/" + actor.getActor_id() + "/films"));
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

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFilm(Film film) {
        // Implementierung
        if (film.getTitle() == null || film.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Title is required").build();
        }

        // Beziehungen behandeln
        if (film.getActors() != null) {
            for (int i = 0; i < film.getActors().size(); i++) {
                Actor actor = film.getActors().get(i);
                if (actor.getActor_id() != null) {
                    film.getActors().set(i, filmService.getEntityManager().find(Actor.class, actor.getActor_id()));
                }
            }
        }
        if (film.getCategories() != null) {
            for (int i = 0; i < film.getCategories().size(); i++) {
                Category category = film.getCategories().get(i);
                if (category.getCategory_id() != null) {
                    film.getCategories().set(i, filmService.getEntityManager().find(Category.class, category.getCategory_id()));
                }
            }
        }

        if (film.getLanguage() != null && film.getLanguage().getLanguage_id() != null) {
            film.setLanguage(filmService.getEntityManager().find(Language.class, film.getLanguage().getLanguage_id()));
        }

        // Persistenz
        try {
            filmService.getEntityManager().getTransaction().begin();
            filmService.getEntityManager().persist(film);
            filmService.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            // Fehlerbehandlung
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error saving the film: " + e.getMessage()).build();
        }


        return Response.created(URI.create("/films/" + film.getFilm_id())).build();
    }

    @GET
    @Path("/count")
    public int getFilmCount() {
        // Implementierung
        return 0;
    }

    @GET
    @Path("/{id}")
    public Film getFilmById(@PathParam("id") int id) {
        // Implementierung
        return new Film();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFilm(@PathParam("id") int id) {
        // Implementierung
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    public Response updateFilm(@PathParam("id") int id, List<String> values) {
        // Implementierung
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/actors")
    public List<Actor> getActorsByFilmId(@PathParam("id") int id) {
        // Implementierung
        return new ArrayList<>();
    }

    @PUT
    @Path("/{id}/actors/{actorId}")
    public Response addActorToFilm(@PathParam("id") int filmId, @PathParam("actorId") int actorId) {
        // Implementierung
        return Response.created(URI.create("/films/" + filmId + "/actors")).build();
    }

    @GET
    @Path("/{id}/categories")
    public List<String> getCategoriesByFilmId(@PathParam("id") int id) {
        // Implementierung
        return new ArrayList<>();
    }

    @PUT
    @Path("/{id}/categories/{category}")
    public Response addCategoryToFilm(@PathParam("id") int filmId, @PathParam("category") String category) {
        // Implementierung
        return Response.created(URI.create("/films/" + filmId + "/categories")).build();
    }
}
