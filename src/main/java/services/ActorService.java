package services;

import dto.ActorDTO;
import dto.FilmDTO;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import utils.FilmsHref;
import utils.UpdateRequestActor;

import java.util.ArrayList;
import java.util.List;

@Named
@Stateless
public class ActorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    FilmService filmService;

    public List<Actor> getActorsByPage(int page) {
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT a FROM Actor a JOIN FETCH a.films",
                Actor.class
        );
        int pageSize = 10;
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
    public List<ActorDTO> getActorDTOs(int page) {
        List<Actor> actors = getActorsByPage(page);
        List<ActorDTO> actorDTOs = new ArrayList<>();

        for (Actor actor : actors) {
            ActorDTO actorDTO = convertToDTO(actor);
            actorDTOs.add(actorDTO);
        }

        return actorDTOs;
    }


    /*
    public List<ActorDTO> getActorDTOs(int page) {
        List<Actor> actors = getActorsByPage(page);
        List<ActorDTO> actorDTOs = new ArrayList<>();

        for (Actor actor : actors) {
            ActorDTO actorDTO = new ActorDTO();
            actorDTO.setId(actor.getActor_id());
            actorDTO.setFirstName(actor.getFirst_name());
            actorDTO.setLastName(actor.getLast_name());

            List<FilmsHref> filmsLinks = new ArrayList<>();
            for (Film film : actor.getFilms()) {
                filmsLinks.add(new FilmsHref("http://localhost:8081/films/" + film.getFilm_id()));
            }
            actorDTO.setFilms(filmsLinks);

            actorDTOs.add(actorDTO);
        }

        return actorDTOs;
    }

     */
    @Transactional
    public Actor createActorFromDTO(ActorDTO actorDTO) {
        Actor actor = convertFromDTO(actorDTO);
        if (actor != null) {
            entityManager.persist(actor);
        }
        return actor;
    }


    /*
    @Transactional
    public Actor createActorFromDTO(ActorDTO actorDTO) {
        Actor actor = new Actor();
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

        entityManager.persist(actor);
        return actor;
    }

     */

    public Integer getActorCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Actor a", Long.class);
        return query.getSingleResult().intValue();
    }

    public Actor getActorById(int id) {
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT a FROM Actor a JOIN FETCH a.films WHERE a.actor_id = :actor_id",
                Actor.class
        );
        query.setParameter("actor_id", id);
        return query.getSingleResult();
    }
    public ActorDTO getActorDTOById(int id) {
        Actor actor = getActorById(id);
        return convertToDTO(actor);
    }


    /*
    public ActorDTO getActorDTOById(int id) {
        Actor actor = getActorById(id);

        if (actor == null) {
            return null;
        }

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getActor_id());
        actorDTO.setFirstName(actor.getFirst_name());
        actorDTO.setLastName(actor.getLast_name());

        List<FilmsHref> filmsLinks = new ArrayList<>();
        for (Film film : actor.getFilms()) {
            filmsLinks.add(new FilmsHref("/films/" + film.getFilm_id()));
        }
        actorDTO.setFilms(filmsLinks);

        return actorDTO;
    }

     */




    @Transactional
    public boolean deleteActor(int actorId) {
        Actor actor = getActorById(actorId);

        if (actor == null) {
            return false;
        }

        // Beziehungen zu Filmen entfernen
        for (Film film : actor.getFilms()) {
            film.getActors().remove(actor);
        }
        actor.getFilms().clear();

        // Actor löschen
        entityManager.flush();
        entityManager.remove(actor);

        return true;
    }

    public boolean updateActor(int id, List<UpdateRequestActor> updates) {
        // Hier muss ich noch prüfen, wenn kein actor gefunden wird, was sollte zurückgeben
        Actor actor = getActorById(id);

        if (actor == null) {
            return false; // Actor nicht gefunden
        }

        for (UpdateRequestActor update : updates) {
            switch (update.getKey()) {
                case "firstName":
                    actor.setFirst_name(update.getValue());
                    break;
                case "lastName":
                    actor.setLast_name(update.getValue());
                    break;
                default:
                    break;
            }
        }

        entityManager.merge(actor);
        return true;
    }

    public List<FilmDTO> getFilmsByActorId(int id) {
        Actor actor = getActorById(id);
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

        return filmDTOs;
    }

    public ActorDTO convertToDTO(Actor actor) {
        if (actor == null) {
            return null;
        }
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getActor_id());
        actorDTO.setFirstName(actor.getFirst_name());
        actorDTO.setLastName(actor.getLast_name());

        List<FilmsHref> filmsLinks = new ArrayList<>();
        for (Film film : actor.getFilms()) {
            filmsLinks.add(new FilmsHref("http://localhost:8081/films/" + film.getFilm_id()));
        }
        actorDTO.setFilms(filmsLinks);

        return actorDTO;
    }

    @Transactional
    public Actor convertFromDTO(ActorDTO actorDTO) {
        if (actorDTO == null) {
            return null;
        }
        Actor actor = new Actor();
        actor.setFirst_name(actorDTO.getFirstName());
        actor.setLast_name(actorDTO.getLastName());

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
        return actor;
    }




}
