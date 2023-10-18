package services;

import entities.Actor;
import entities.Film;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Named
@Stateless
public class ActorService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Actor> getFirst10Actors() {
        TypedQuery<Actor> query = entityManager.createQuery("SELECT a FROM Actor a", Actor.class);
        query.setMaxResults(10);
        List<Actor> actors = query.getResultList();
        for (Actor actor : actors) {
            actor.getFilms().size(); // initialisiert die films-Liste
        }
        return actors;
    }

    public Integer getActorCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Actor a", Long.class);
        return query.getSingleResult().intValue();
    }

    public Actor getActorById(int id) {
        return entityManager.find(Actor.class, id);
    }

    public List<Film> getFilmsByActorId(int id){
        TypedQuery<Film> query = entityManager.createQuery(
                "SELECT f FROM Film f JOIN f.actors a WHERE a.actor_id = :actor_id",
                Film.class
        );
        query.setParameter("actor_id", id);
        return query.getResultList();
    }
}
