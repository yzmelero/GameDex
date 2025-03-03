package cat.copernic.gamedex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import cat.copernic.gamedex.entity.Category;
import cat.copernic.gamedex.logic.CategoryLogic;
import cat.copernic.gamedex.repository.CategoryRepository;

public class CategoryTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryLogic categoryLogic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory_CategoryAlreadyExists() {
        Category category = new Category();
        category.setNameCategory("testCategory");

        when(categoryRepository.findById(category.getNameCategory())).thenReturn(Optional.of(category));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryLogic.createCategory(category);
        });

        assertEquals("Category already exists", exception.getMessage());
    }

    @Test
    public void testCreateCategory_Success() {
        Category category = new Category();
        category.setNameCategory("testCategory");

        when(categoryRepository.findById(category.getNameCategory())).thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryLogic.createCategory(category);

        assertNotNull(createdCategory);
        assertEquals("testCategory", createdCategory.getNameCategory());
    }

    @Test
    public void testModifyCategory_CategoryNotFound() {
        Category category = new Category();
        category.setNameCategory("testCategory");

        when(categoryRepository.findById(category.getNameCategory())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryLogic.modifyCategory(category);
        });

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    public void testModifyCategory_Success() {
        Category category = new Category();
        category.setNameCategory("testCategory");
        category.setDescription("newDescription");

        Category existingCategory = new Category();
        existingCategory.setNameCategory("testCategory");
        existingCategory.setDescription("oldDescription");

        when(categoryRepository.findById(category.getNameCategory())).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category modifiedCategory = categoryLogic.modifyCategory(category);

        assertNotNull(modifiedCategory);
        assertEquals("newDescription", modifiedCategory.getDescription());
    }

    @Test
    public void testDeleteCategory_CategoryNotFound() {
        String nameCategory = "testCategory";

        when(categoryRepository.findById(nameCategory)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryLogic.deleteCategory(nameCategory);
        });

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    public void testDeleteCategory_Success() {
        String nameCategory = "testCategory";

        Category category = new Category();
        category.setNameCategory(nameCategory);

        when(categoryRepository.findById(nameCategory)).thenReturn(Optional.of(category));

        assertDoesNotThrow(() -> {
            categoryLogic.deleteCategory(nameCategory);
        });

        verify(categoryRepository, times(1)).deleteById(nameCategory);
    }

    @Test
    public void testGetCategoryById_CategoryNotFound() {
        String categoryId = "testCategory";

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryLogic.getCategoryById(categoryId);
        });

        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    public void testGetCategoryById_Success() {
        String categoryId = "testCategory";

        Category category = new Category();
        category.setNameCategory(categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category foundCategory = categoryLogic.getCategoryById(categoryId);

        assertNotNull(foundCategory);
        assertEquals(categoryId, foundCategory.getNameCategory());
    }
}