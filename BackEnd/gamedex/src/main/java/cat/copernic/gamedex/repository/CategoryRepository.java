package cat.copernic.gamedex.repository;

import java.util.List;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.copernic.gamedex.entity.Category;
@Repository
public interface CategoryRepository extends MongoRepository <Category, String> {
    
    List<Category> findByNameCategoryContaining(String nameCategory);
    List<Category> findAllByOrderByNameCategoryAsc();
}