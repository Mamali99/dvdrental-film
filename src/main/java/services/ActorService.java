package services;

import entities.*;
import jakarta.ejb.Stateless;
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


    public List<Actor> getFilmsByActorId(int id) {
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT a FROM Actor a JOIN FETCH a.films",
                Actor.class
        );
        query.setMaxResults(10);
        return query.getResultList();
    }

    public void createActor(Actor actor) {
        entityManager.persist(actor);
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
                // Sie können weitere Fälle hinzufügen, um andere Eigenschaften des Actors zu aktualisieren
                default:
                    // Unbekannter Schlüssel, Sie können eine Ausnahme auslösen oder ihn einfach ignorieren
                    break;
            }
        }

        entityManager.merge(actor); // Aktualisieren des Actor-Objekts in der Datenbank
        return true;
    }

}
