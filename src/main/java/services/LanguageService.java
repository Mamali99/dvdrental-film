package services;

import entities.Film;
import entities.Language;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
@Named
@Stateless
public class LanguageService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Language> get10Languages() {

        TypedQuery<Language> query = entityManager.createQuery("SELECT l FROM Language l", Language.class);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
