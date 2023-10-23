package services;

import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Named
@Stateless
public class ActorService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    FilmService filmService;

    public List<Actor> getFirst10Actors() {
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT a FROM Actor a JOIN FETCH a.films",
                Actor.class
        );
        query.setMaxResults(10);
        return query.getResultList();
    }

    public List<ActorDTO> getActorDTOs(int page) {
        List<Actor> actors = getFirst10Actors();
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

        return actorDTOs;
    }

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


    public List<Actor> getFilmsByActorId(int id) {
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT a FROM Actor a JOIN FETCH a.films",
                Actor.class
        );
        query.setMaxResults(10);
        return query.getResultList();
    }



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

}
