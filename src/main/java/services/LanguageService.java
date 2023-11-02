package services;

import entities.Language;
import dto.LanguageDTO;
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

    public List<LanguageDTO> getLanguages() {
        TypedQuery<Language> query = entityManager.createQuery("SELECT l FROM Language l", Language.class);

        List<Language> languages = query.getResultList();

        List<LanguageDTO> languageDTOs = new ArrayList<>();
        for (Language language : languages) {
            LanguageDTO dto = new LanguageDTO();
            dto.setLanguageId(language.getLanguage_id());
            dto.setName(language.getName());
            dto.setLastUpdate(language.getLast_update());
            languageDTOs.add(dto);
        }

        return languageDTOs;
    }

    public Language getLanguageByName(String language) {
        Language l = entityManager.createQuery("SELECT l FROM Language l WHERE l.name = :name", Language.class)
                .setParameter("name", language)
                .getSingleResult();

        System.out.println("\n" + l.getLanguage_id().toString() + "\n");

        return l;
    }
}
