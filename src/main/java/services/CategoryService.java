package services;

import entities.Category;
import entities.CategoryDTO;
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
public class CategoryService {

    @PersistenceContext
    EntityManager entityManager;


    public List<CategoryDTO> getCategory(){
        List<Category> categories = entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : categories) {
            categoryDTOs.add(new CategoryDTO(category.getCategory_id(), category.getName(), category.getLast_update()));
        }
        return categoryDTOs;
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
