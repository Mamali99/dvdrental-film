package services;

import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
@Named
@Stateless
public class FilmService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Film> getFirst20Films() {
        TypedQuery<Film> query = entityManager.createQuery("SELECT f FROM Film f", Film.class);
        query.setMaxResults(20);
        return query.getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getFilmCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(f) FROM Film f", Long.class);
        return query.getSingleResult().intValue();
    }

    public Film getFilmById(int id) {
        return entityManager.find(Film.class, id);
    }

    public List<ActorDTO> getActorsByFilmId(int id) {
        Film film = entityManager.find(Film.class, id);
        if (film == null) {
            return null;
        }

        List<ActorDTO> actorDTOs = new ArrayList<>();
        for (Actor actor : film.getActors()) {
            ActorDTO actorDTO = new ActorDTO();
            actorDTO.setId(actor.getActor_id());
            actorDTO.setFirstName(actor.getFirst_name());
            actorDTO.setLastName(actor.getLast_name());

            // Setzen der Filme, in denen der Schauspieler gespielt hat
            List<FilmsHref> filmsLinks = new ArrayList<>();
            for (Film actorFilm : actor.getFilms()) {
                filmsLinks.add(new FilmsHref("/films/" + actorFilm.getFilm_id()));
            }
            actorDTO.setFilms(filmsLinks);

            actorDTOs.add(actorDTO);
        }

        return actorDTOs;
    }

    public List<CategoryDTO> getCategoriesByFilmId(int id){
        Film film = entityManager.find(Film.class, id);
        if (film == null) {
            return null;
        }

        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : film.getCategories()) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    category.getCategory_id(),
                    category.getName(),
                    category.getLast_update()
            );
            categoryDTOs.add(categoryDTO);
        }

        return categoryDTOs;
    }
}
