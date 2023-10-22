package resources;

import entities.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.ActorService;
import services.CategoryService;
import services.FilmService;
import services.LanguageService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/films")
public class FilmResource {

    @Inject
    FilmService filmService;

    @Inject
    LanguageService languageService;

    @Inject
    ActorService actorService;

    @Inject
    CategoryService categoryService;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFilm(FilmDTO filmDTO) {
        Film film = filmService.createFilmFromDTO(filmDTO);
        return Response.created(URI.create("/films/" + film.getFilm_id())).build();
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
        Film film = filmService.getFilmById(id);
        if (film == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Film not found for ID: " + id).build();
        }

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
        for (Actor actor : film.getActors()) {
            actorsLinks.add(new FilmsHref("/actors/" + actor.getActor_id() + "/films"));
        }
        filmDTO.setActors(actorsLinks);

        List<String> categories = new ArrayList<>();
        for (Category category : film.getCategories()) {
            categories.add(category.getName());
        }
        filmDTO.setCategories(categories);

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
        filmService.addActorToFilm(filmId, actorId);
        return Response.created(URI.create("/films/" + filmId + "/actors")).build();
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
