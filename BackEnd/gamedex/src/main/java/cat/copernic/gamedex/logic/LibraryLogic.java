package cat.copernic.gamedex.logic;

import cat.copernic.gamedex.entity.Library;
import cat.copernic.gamedex.repository.LibraryRepository;
import cat.copernic.gamedex.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryLogic {

    @Autowired
    private LibraryRepository libraryRepository;

    public List<Library> getLibraryByUser(String username) {
        return libraryRepository.findByUserUsername(username);
    }

    Logger log = LoggerFactory.getLogger(LibraryLogic.class);

    public Library addGameToLibrary(Library library) {
        System.out.println("Usuari rebut en backend: " + library.getUser().getUsername());
        Optional<Library> existingGamesInLibrary = libraryRepository.findByUserAndVideogame(
                library.getUser().getUsername(),
                library.getVideogame().getGameId());
        System.out.println("Username: " + library.getUser().getUsername());
        System.out.println("Game ID: " + library.getVideogame().getGameId());

        // Si ja existeix almenys un registre, bloquegem l'addició
        if (!existingGamesInLibrary.isEmpty()) {
            throw new RuntimeException("Game already in library");
        }
        return libraryRepository.save(library);
    }

    public List<Library> getLibraryByGame(String gameId) {
        return libraryRepository.findByVideogameGameId(gameId);

    }

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

    public int countByCategory(String username, String state) {
        return libraryRepository.countByUserUsernameAndState(username, state);
    }

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
        return count > 0 ? sum / count : 0.0;// Retorna la mitjana si hi ha valoracions
    }

    public Optional<Library> getLibraryEntry(String gameId, String username) {
        return libraryRepository.findByUserAndVideogame(gameId, username);
    }

    public Library updateGameInLibrary(Library library) {

        Library existingLibrary = libraryRepository.findByUserAndVideogame(
                library.getVideogame().getGameId(),
                library.getUser().getUsername()).orElseThrow(() -> new RuntimeException("Game not found in library"));

        existingLibrary.setState(library.getState());
        existingLibrary.setDescription(library.getDescription());
        existingLibrary.setRating(library.getRating());

        return libraryRepository.save(existingLibrary); // Retornem el 'Library' actualitzat.

    }

}
