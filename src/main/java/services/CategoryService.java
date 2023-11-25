package services;

import entities.Category;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CategoryService {

    @PersistenceContext
    private EntityManager entityManager;


    public List<String> getCategory(){
        List<Category> categories = entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }


    public Category getCategoryByName(String categoryName) {

        return entityManager.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class)
                .setParameter("name", categoryName)
                .getSingleResult();
    }

    public Category getCategoryById(int id){
        return entityManager.find(Category.class, id);
    }
}
