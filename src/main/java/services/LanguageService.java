package services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LanguageService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getAllLanguages() {
        TypedQuery<String> query = entityManager.createQuery("SELECT l FROM Language l", String.class);
        return query.getResultList();
    }
}
