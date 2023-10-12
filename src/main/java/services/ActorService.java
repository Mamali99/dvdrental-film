package services;

import entities.Actor;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Named
public class ActorService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Actor> getFirst10Actors() {
        TypedQuery<Actor> query = entityManager.createQuery("SELECT a FROM Actor a", Actor.class);
        query.setMaxResults(10);

        return query.getResultList();
    }

    public Integer getActorCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Actor a", Long.class);
        return query.getSingleResult().intValue();
    }

    public Actor getActorById(int id) {
        return entityManager.find(Actor.class, id);
    }
}
