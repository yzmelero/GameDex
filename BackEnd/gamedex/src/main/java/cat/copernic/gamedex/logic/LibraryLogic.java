package cat.copernic.gamedex.logic;

import cat.copernic.gamedex.entity.Library;
import cat.copernic.gamedex.repository.LibraryRepository;

import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Lògica de negoci per gestionar la biblioteca de videojocs.
 */
@Service
public class LibraryLogic {

    @Autowired
    private LibraryRepository libraryRepository;

    Logger log = LoggerFactory.getLogger(LibraryLogic.class);

    /**
     * Obté la biblioteca d'un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return Una llista de biblioteques de l'usuari.
     */
    public List<Library> getLibraryByUser(String username) {
        return libraryRepository.findByUserUsername(username);
    }

    /**
     * Afegeix un joc a la biblioteca d'un usuari.
     *
     * @param library La biblioteca que conté el joc i l'usuari.
     * @return La biblioteca actualitzada amb el nou joc.
     */
    public Library addGameToLibrary(Library library) {
        System.out.println("Usuari rebut en backend: " + library.getUser().getUsername());
        Optional<Library> existingGamesInLibrary = libraryRepository
                .findByUserAndVideogame(library.getUser().getUsername(), library.getVideogame().getGameId());
        System.out.println("Username: " + library.getUser().getUsername());
        System.out.println("Game ID: " + library.getVideogame().getGameId());

        // Si ja existeix almenys un registre, bloquegem l'addició
        if (!existingGamesInLibrary.isEmpty()) {
            throw new RuntimeException("Game already in library");
        }
        return libraryRepository.save(library);
    }

    /**
     * Obté la biblioteca d'un joc pel seu ID.
     *
     * @param gameId L'ID del joc.
     * @return Una llista de biblioteques que contenen el joc.
     */
    public List<Library> getLibraryByGame(String gameId) {
        return libraryRepository.findByVideogameGameId(gameId);
    }

    /**
     * Elimina un joc de la biblioteca d'un usuari.
     *
     * @param gameId   L'ID del joc.
     * @param username El nom d'usuari.
     */
    public void deleteGameFromLibrary(String gameId, String username) {
        try {
            Optional<Library> library = libraryRepository.findByUserAndVideogame(gameId, username);
            if (library.isEmpty()) {
                throw new RuntimeException("Game not found in library");
            }
            log.info("LibraryLogic gameId: " + gameId);
            libraryRepository.delete(library.get());
        } catch (RuntimeException e) {
            throw e; // Re-throw the original RuntimeException
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error deleting game from library");
        }
    }

    /**
     * Compta el nombre de jocs en una categoria específica per a un usuari.
     *
     * @param username El nom d'usuari.
     * @param state    L'estat dels jocs.
     * @return El nombre de jocs en la categoria especificada.
     */
    public int countByCategory(String username, String state) {
        return libraryRepository.countByUserUsernameAndState(username, state);
    }

    /**
     * Obté la mitjana de puntuacions d'un joc.
     *
     * @param gameId L'ID del joc.
     * @return La mitjana de puntuacions del joc.
     */
    public double getAverageRating(String gameId) {
        List<Library> ratings = libraryRepository.findAllByVideogame(gameId);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int count = 0;
        for (Library entry : ratings) {
            if (entry.getRating() != 0.0) {
                sum += entry.getRating();
                count++;
            }
        }
        return count > 0 ? sum / count : 0.0; // Retorna la mitjana si hi ha valoracions
    }
}