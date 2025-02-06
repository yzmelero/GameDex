package cat.copernic.gamedex.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.copernic.gamedex.entity.Category;
import cat.copernic.gamedex.repository.CategoryRepository;

@Service
public class CategoryLogic {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
