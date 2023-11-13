package services;

import entities.Language;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
@Named
@Stateless
public class LanguageService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getLanguages() {
        TypedQuery<Language> query = entityManager.createQuery("SELECT l FROM Language l", Language.class);
        List<Language> languages = query.getResultList();

        List<String> languageNames = new ArrayList<>();
        for (Language language : languages) {
            languageNames.add(language.getName().trim());
        }

        return languageNames;
    }


    public Language getLanguageByName(String language) {
        Language l = entityManager.createQuery("SELECT l FROM Language l WHERE l.name = :name", Language.class)
                .setParameter("name", language)
                .getSingleResult();

        return l;
    }
}
