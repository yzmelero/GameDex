package cat.copernic.gamedex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
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
    public void testCreateCategory() {
        Category category = new Category("Action", "Action games", null);
        when(categoryRepository.findById(category.getNameCategory())).thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryLogic.createCategory(category);

        assertNotNull(createdCategory);
        assertEquals("Action", createdCategory.getNameCategory());
        verify(categoryRepository, times(1)).findById(category.getNameCategory());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {
        String categoryName = "Action";
        Category category = new Category(categoryName, "Action games", null);
        when(categoryRepository.findById(categoryName)).thenReturn(Optional.of(category));

        categoryLogic.deleteCategory(categoryName);

        verify(categoryRepository, times(1)).findById(categoryName);
        verify(categoryRepository, times(1)).deleteById(categoryName);
    }

    @Test
    public void testModifyCategory() {
        Category oldCategory = new Category("Action", "Old description", null);
        Category newCategory = new Category("Action", "New description", null);
        when(categoryRepository.findById(oldCategory.getNameCategory())).thenReturn(Optional.of(oldCategory));
        when(categoryRepository.save(oldCategory)).thenReturn(oldCategory);

        Category modifiedCategory = categoryLogic.modifyCategory(newCategory);

        assertNotNull(modifiedCategory);
        assertEquals("New description", modifiedCategory.getDescription());
        verify(categoryRepository, times(1)).findById(oldCategory.getNameCategory());
        verify(categoryRepository, times(1)).save(oldCategory);
    }

    @Test
    public void testGetAllCategory() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Action", "Action games", null));
        categories.add(new Category("Adventure", "Adventure games", null));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryLogic.getAllCategory();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }
}