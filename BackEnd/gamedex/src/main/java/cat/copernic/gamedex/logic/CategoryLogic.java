package cat.copernic.gamedex.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.copernic.gamedex.entity.Category;
import cat.copernic.gamedex.repository.CategoryRepository;

/**
 * Lògica de negoci per gestionar les categories.
 */
@Service
public class CategoryLogic {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Crea una nova categoria.
     *
     * @param category La categoria a crear.
     * @return La categoria creada.
     */
    public Category createCategory(Category category) {
        try {
            Optional<Category> oldCategory = categoryRepository.findById(category.getNameCategory());
            if (oldCategory.isPresent()) {
                throw new RuntimeException("Category already exists");
            }
            return categoryRepository.save(category);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating Category");
        }
    }

    /**
     * Elimina una categoria pel seu nom.
     *
     * @param nameCategory El nom de la categoria a eliminar.
     */
    public void deleteCategory(String nameCategory) {
        try {
            Optional<Category> category = categoryRepository.findById(nameCategory);
            if (category.isEmpty()) {
                throw new RuntimeException("Category not found");
            }
            categoryRepository.deleteById(nameCategory);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error deleting category", e);
        }
    }

    /**
     * Modifica una categoria existent.
     *
     * @param category La categoria amb les dades actualitzades.
     * @return La categoria modificada.
     */
    public Category modifyCategory(Category category) {
        try {
            Optional<Category> oldCategory = categoryRepository.findById(category.getNameCategory());
            if (oldCategory.isEmpty()) {
                throw new RuntimeException("Category not found");
            } else {
                Category newCategory = oldCategory.get();

                if (category.getDescription() != null
                        && !category.getDescription().equals(newCategory.getDescription())) {
                    newCategory.setDescription(category.getDescription());
                }

                if (category.getCategoryPhoto() != null
                        && !category.getCategoryPhoto().equals(newCategory.getCategoryPhoto())) {
                    newCategory.setCategoryPhoto(category.getCategoryPhoto());
                }

                return categoryRepository.save(newCategory);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying category", e);
        }
    }

    /**
     * Obté totes les categories.
     *
     * @return Una llista de totes les categories.
     */
    public List<Category> getAllCategory() {
        try {
            return categoryRepository.findAllByOrderByNameCategoryAsc();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all Categorys");
        }
    }

    /**
     * Obté una categoria pel seu ID.
     *
     * @param categoryId L'ID de la categoria.
     * @return La categoria amb l'ID especificat.
     */
    public Category getCategoryById(String categoryId) {
        try {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isEmpty()) {
                throw new RuntimeException("Category not found");
            } else {
                return category.get();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting the category", e);
        }
    }

    /**
     * Cerca categories basades en una consulta.
     *
     * @param query La consulta de cerca.
     * @return Una llista de categories que coincideixen amb la consulta.
     */
    public List<Category> searchCategories(String query) {
        return categoryRepository.findByNameCategoryContaining(query);
    }
}