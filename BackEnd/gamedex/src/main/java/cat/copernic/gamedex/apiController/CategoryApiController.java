package cat.copernic.gamedex.apiController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.copernic.gamedex.entity.Category;
import cat.copernic.gamedex.logic.CategoryLogic;

/**
 * Controlador REST per gestionar les categories.
 */
@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryApiController {

    Logger log = LoggerFactory.getLogger(CategoryApiController.class);

    @Autowired
    private CategoryLogic categoryLogic;

    /**
     * Crea una nova categoria.
     *
     * @param category La categoria a crear.
     * @return La categoria creada.
     */
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        log.info("Creating category: " + category.toString());
        Category newCategory = categoryLogic.createCategory(category);
        return ResponseEntity.ok(newCategory);
    }

    /**
     * Elimina una categoria pel seu nom.
     *
     * @param nameCategory El nom de la categoria a eliminar.
     * @return Un missatge indicant que la categoria s'ha eliminat correctament.
     */
    @DeleteMapping("/delete/{nameCategory}")
    public ResponseEntity<String> deleteCategory(@PathVariable String nameCategory) {
        log.info("Deleting category: " + nameCategory);
        categoryLogic.deleteCategory(nameCategory);
        return ResponseEntity.ok("Category deleted successfully");
    }

    /**
     * Modifica una categoria existent.
     *
     * @param category La categoria amb les modificacions.
     * @return La categoria modificada.
     */
    @PutMapping("/modify/{nameCategory}")
    public ResponseEntity<Category> modifyCategory(@RequestBody Category category) {
        log.info("Modifying category: " + category.toString());
        Category updatedCategory = categoryLogic.modifyCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Obté totes les categories.
     *
     * @return Una llista de totes les categories.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryLogic.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    /**
     * Obté una categoria pel seu ID.
     *
     * @param categoryId L'ID de la categoria a obtenir.
     * @return La categoria amb l'ID especificat.
     */
    @GetMapping("/get/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId) {
        log.info("Fetching category with ID: " + categoryId);
        Category category = categoryLogic.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    /**
     * Cerca categories basades en una consulta.
     *
     * @param query La consulta de cerca.
     * @return Una llista de categories que coincideixen amb la consulta.
     */
    @GetMapping("/filter/{query}")
    public ResponseEntity<List<Category>> searchCategories(@PathVariable String query) {
        log.info("Searching categories with query: " + query);
        List<Category> categories = categoryLogic.searchCategories(query);
        return ResponseEntity.ok(categories);
    }
}