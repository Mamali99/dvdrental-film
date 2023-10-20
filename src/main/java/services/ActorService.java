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
        TypedQuery<Actor> query = entityManager.createQuery(
                "SELECT a FROM Actor a JOIN FETCH a.films",
                Actor.class
        );
        query.setMaxResults(10);
        return query.getResultList();
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


    public List<Film> getFilmsByActorId(int id){
        TypedQuery<Film> query = entityManager.createQuery(
                "SELECT f FROM Film f JOIN f.actors a WHERE a.actor_id = :actor_id",
                Film.class
        );
        query.setParameter("actor_id", id);
        return query.getResultList();
    }
}
