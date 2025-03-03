package cat.copernic.gamedex.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.copernic.gamedex.entity.Category;

/**
 * Repositori per gestionar les operacions de la base de dades per a les
 * categories.
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

        /**
         * Cerca categories que continguin una cadena específica en el seu nom.
         *
         * @param nameCategory La cadena a cercar en el nom de la categoria.
         * @return Una llista de categories que contenen la cadena especificada en el
         *         seu nom.
         */
    List<Category> findByNameCategoryContaining(String nameCategory);
    List<Category> findAllByOrderByNameCategoryAsc();
}