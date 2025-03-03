package cat.copernic.gamedex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.copernic.gamedex.entity.User;

/**
 * Repositori per gestionar les operacions de la base de dades per als usuaris.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Cerca usuaris que continguin una cadena específica en el seu nom d'usuari.
     *
     * @param username La cadena a cercar en el nom d'usuari.
     * @return Una llista d'usuaris que contenen la cadena especificada en el seu
     *         nom d'usuari.
     */
    List<User> findByUsernameContaining(String username);

    /**
     * Cerca un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return Un usuari que coincideix amb el nom d'usuari especificat.
     */
    Optional<User> findByUsername(String username);

    /**
     * Cerca un usuari pel seu correu electrònic.
     *
     * @param email El correu electrònic.
     * @return Un usuari que coincideix amb el correu electrònic especificat.
     */
    Optional<User> findByEmail(String email);

    /**
     * Cerca un usuari pel seu telèfon.
     *
     * @param telephone El número de telèfon.
     * @return Un usuari que coincideix amb el número de telèfon especificat.
     */
    Optional<User> findByTelephone(int telephone);

    /**
     * Cerca usuaris pel seu estat.
     *
     * @param b L'estat dels usuaris (true per a actius, false per a inactius).
     * @return Una llista d'usuaris que coincideixen amb l'estat especificat.
     */
    List<User> findByState(boolean b);

    /**
     * Cerca un usuari pel seu nom d'usuari i correu electrònic.
     *
     * @param username El nom d'usuari.
     * @param email    El correu electrònic.
     * @return Un usuari que coincideix amb el nom d'usuari i el correu electrònic
     *         especificats.
     */
    Optional<User> findByUsernameAndEmail(String username, String email);
}