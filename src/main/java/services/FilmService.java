package services;

import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Named
@Stateless
public class FilmService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    LanguageService languageService;

    @Inject
    ActorService actorService;

    @Inject
    CategoryService categoryService;

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
    @Transactional
    public Film createFilmFromDTO(FilmDTO filmDTO) {
        Film film = new Film();

        // Setzen der einfachen Film-Attribute
        film.setTitle(filmDTO.getTitle());
        film.setDescription(filmDTO.getDescription());
        film.setRelease_year(filmDTO.getReleaseYear());
        film.setRental_duration(filmDTO.getRentalDuration());
        film.setRental_rate(filmDTO.getRentalRate());
        film.setLength(filmDTO.getLength());
        film.setReplacement_cost(filmDTO.getReplacementCost());
        film.setRating(filmDTO.getRating());

        // Setzen der Sprache für den Film
        if (filmDTO.getLanguage() != null) {
            Language language = languageService.getLanguageByName(filmDTO.getLanguage());
            if (language != null) {
                film.setLanguage(language);
            }
        }

        // Hinzufügen von Schauspielern zum Film
        if (filmDTO.getActors() != null && !filmDTO.getActors().isEmpty()) {
            for (FilmsHref actorHref : filmDTO.getActors()) {
                if (actorHref.getHref() != null) {
                    String actorIdStr = actorHref.getHref().replaceAll("[^0-9]", "");
                    Integer actorId = Integer.parseInt(actorIdStr);
                    Actor actor = actorService.getActorById(actorId);
                    if (actor != null) {
                        film.getActors().add(actor);
                    }
                }
            }
        }

        // Hinzufügen von Kategorien zum Film
        if (filmDTO.getCategories() != null && !filmDTO.getCategories().isEmpty()) {
            for (String categoryName : filmDTO.getCategories()) {
                Category category = categoryService.getCategoryByName(categoryName);
                if (category != null) {
                    film.getCategories().add(category);
                }
            }
        }

        // Überprüfen, ob der Film bereits eine ID hat
        if (film.getFilm_id() != null) {
            throw new IllegalArgumentException("Der Film hat bereits eine ID und sollte nicht erneut erstellt werden.");
        }

        // Speichern des Films in der Datenbank
        entityManager.persist(film);

        // Rückgabe des gespeicherten Films
        return film;
    }

    /*
    @Transactional
    public Film createFilm(Film film){
        // Überprüfen, ob der Film bereits eine ID hat (was bedeuten würde, dass er bereits in der Datenbank existiert)
        if (film.getFilm_id() != null) {
            throw new IllegalArgumentException("Der Film hat bereits eine ID und sollte nicht erneut erstellt werden.");
        }

        // Speichern des Films in der Datenbank
        entityManager.persist(film);

        // Rückgabe des gespeicherten Films
        return film;

    }

     */

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
