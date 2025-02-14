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

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryApiController {

    Logger log = LoggerFactory.getLogger(CategoryApiController.class);

    @Autowired
    private CategoryLogic categoryLogic;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        log.info("Creating category: " + category.toString());
        Category newCategory = categoryLogic.createCategory(category);
        return ResponseEntity.ok(newCategory);
    }

    @DeleteMapping("/delete/{nameCategory}")
    public ResponseEntity<String> deleteCategory(@PathVariable String nameCategory) {
        log.info("Deleting category: " + nameCategory);
        categoryLogic.deleteCategory(nameCategory);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @PutMapping("/modify")
    public ResponseEntity<Category> modifyCategory(@RequestBody Category category) {
        log.info("Modifying category: " + category.toString());
        Category updatedCategory = categoryLogic.modifyCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryLogic.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId) {
        log.info("Fetching category with ID: " + categoryId);
        Category category = categoryLogic.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }
}

