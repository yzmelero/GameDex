package cat.copernic.gamedex.repository;

import cat.copernic.gamedex.entity.Library;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositori per gestionar les operacions de la base de dades per a les
 * biblioteques de videojocs.
 */
@Repository
public interface LibraryRepository extends MongoRepository<Library, String> {

    /**
     * Cerca totes les biblioteques que contenen un videojoc específic.
     *
     * @param gameId L'ID del videojoc.
     * @return Una llista de biblioteques que contenen el videojoc especificat.
     */
    public List<Library> findAllByVideogame(String gameId);

    /**
     * Cerca una biblioteca per l'usuari i el videojoc.
     *
     * @param gameId   L'ID del videojoc.
     * @param username El nom d'usuari.
     * @return Una biblioteca que coincideix amb l'usuari i el videojoc
     *         especificats.
     */
    @Query("{ 'videogame.gameId' : :#{#gameId}, 'user.username' : :#{#username} }")
    Optional<Library> findByUserAndVideogame(@Param("gameId") String gameId, 
                                             @Param("username") String username
                                             );

    /**
     * Cerca totes les biblioteques d'un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return Una llista de biblioteques de l'usuari especificat.
     */
    public List<Library> findByUserUsername(String username);

    /**
     * Cerca totes les biblioteques que contenen un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return Una llista de biblioteques que contenen el videojoc especificat.
     */
    List<Library> findByVideogameGameId(String gameId);

    /**
     * Compta el nombre de jocs en una categoria específica per a un usuari.
     *
     * @param username El nom d'usuari.
     * @param state    L'estat dels jocs.
     * @return El nombre de jocs en la categoria especificada.
     */
    int countByUserUsernameAndState(String username, String state);
}
