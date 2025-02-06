package cat.copernic.gamedex.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.copernic.gamedex.entity.Category;

@Repository
public interface CategoryRepository extends MongoRepository <Category, String> {
    
}