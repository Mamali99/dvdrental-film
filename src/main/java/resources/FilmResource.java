package resources;

import entities.Actor;
import entities.Film;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/films")
public class FilmResource {
    @GET
    public List<Film> getFilms(@QueryParam("page") @DefaultValue("1") int page) {
        // Implementierung
        return new ArrayList<>();
    }

    @POST
    public Response createFilm(Film film) {
        // Implementierung
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
