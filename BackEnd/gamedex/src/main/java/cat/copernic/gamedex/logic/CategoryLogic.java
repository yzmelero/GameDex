package cat.copernic.gamedex.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.copernic.gamedex.entity.Category;
import cat.copernic.gamedex.repository.CategoryRepository;

@Service
public class CategoryLogic {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
       try {
            Optional<Category> oldCategory = categoryRepository.findById(category.getNameCategory());
            if (oldCategory.isPresent()) {
                throw new RuntimeException("Category already exists");
            }
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating Category");
        }
    }

    public void deleteCategory(String nameCategory) {
        try {
            Optional<Category> category = categoryRepository.findById(nameCategory);
            if (category.isEmpty()) {
                throw new RuntimeException("Category not found");
            }
            categoryRepository.deleteById(nameCategory);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error deleting category", e);
        }
    }
    
    public Category modifyCategory(Category category) {
        try {
            Optional<Category> oldCategory = categoryRepository.findById(category.getNameCategory());
            if (oldCategory.isEmpty()) {
                throw new RuntimeException("Category not found");
            } else {
                Category newCategory = oldCategory.get();
    
                if (category.getDescription() != null && 
                    !category.getDescription().equals(newCategory.getDescription())) {
                    newCategory.setDescription(category.getDescription());
                }
    
                if (category.getCategoryPhoto() != null && 
                    !category.getCategoryPhoto().equals(newCategory.getCategoryPhoto())) {
                    newCategory.setCategoryPhoto(category.getCategoryPhoto());
                }
    
                return categoryRepository.save(newCategory);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying category", e);
        }
    }

    public List<Category> getAllCategory() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all Categorys");
        }
    }
}
