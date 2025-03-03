package cat.copernic.gamedex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cat.copernic.gamedex.entity.Videogame;

/**
 * Repositori per gestionar les operacions de la base de dades per als
 * videojocs.
 */
@Repository
public interface VideogameRepository extends MongoRepository<Videogame, String> {

    /**
     * Cerca un videojoc pel seu nom.
     *
     * @param nameGame El nom del videojoc.
     * @return Un videojoc que coincideix amb el nom especificat.
     */
    Optional<Videogame> findByNameGame(String nameGame);

    /**
     * Cerca videojocs pel seu estat.
     *
     * @param state L'estat dels videojocs (true per a actius, false per a
     *              inactius).
     * @return Una llista de videojocs que coincideixen amb l'estat especificat.
     */
    List<Videogame> findByState(boolean state);

    /**
     * Cerca videojocs per la seva categoria i estat actiu.
     * @param category
     * @param state
     * @return
     */
    List<Videogame> findByCategoryAndState(String category, boolean state);

    /**
     * Cerca videojocs pel seu nom.
     * @param nameGame
     * @return
     */
    List<Videogame> findByNameGameContaining(String nameGame);
}