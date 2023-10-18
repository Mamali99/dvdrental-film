package services;

import entities.Film;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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
}
