package cat.copernic.gamedex.apiController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import cat.copernic.gamedex.logic.LibraryLogic;
import cat.copernic.gamedex.entity.Library;

/**
 * Controlador REST per gestionar la biblioteca de videojocs.
 */
@RestController
@RequestMapping("/api/library")
@CrossOrigin(origins = "*")
public class LibraryApiController {

    @Autowired
    private LibraryLogic libraryLogic;

    Logger log = LoggerFactory.getLogger(LibraryApiController.class);

    /**
     * Afegeix un joc a la biblioteca d'un usuari.
     *
     * @param library La biblioteca que conté el joc i l'usuari.
     * @return La biblioteca actualitzada amb el nou joc.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addGameToLibrary(@RequestBody Library library) {
        System.out.println("Intentant afegir joc amb ID: " + library.getVideogame().getGameId() + " per a l'usuari: "
                + library.getUser().getUsername());

        try {
            Library newGameToLibrary = libraryLogic.addGameToLibrary(library);
            return ResponseEntity.status(HttpStatus.CREATED).body(newGameToLibrary);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Game already in library");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Obté la biblioteca d'un usuari.
     *
     * @param username El nom d'usuari.
     * @return La biblioteca de l'usuari.
     */
    @GetMapping("/get")
    public List<Library> getLibrary(@RequestParam String username) {
        return libraryLogic.getLibraryByUser(username);
    }

    /**
     * Obté els comentaris d'un joc específic.
     *
     * @param gameId L'ID del joc.
     * @return Una llista de biblioteques que contenen comentaris sobre el joc.
     */
    @GetMapping("/comments/{gameId}")
    public ResponseEntity<List<Library>> getCommentsByGame(@PathVariable String gameId) {
        log.info("Rebent consulta per gameId: " + gameId);
        try {
            List<Library> libraries = libraryLogic.getLibraryByGame(gameId); // Filtrar per gameId
            log.info("Resultat de la cerca: " + libraries.size() + " elements");
            if (libraries.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No content
            }
            return ResponseEntity.ok(libraries);
        } catch (Exception e) {
            log.error("Error recuperant comentaris per al videojoc " + gameId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Error intern
        }
    }

    /**
     * Elimina un joc de la biblioteca d'un usuari.
     *
     * @param gameId   L'ID del joc.
     * @param username El nom d'usuari.
     * @return Una resposta sense contingut si l'eliminació és correcta.
     */
    @DeleteMapping("/delete/{gameId}/{username}")
    public ResponseEntity<Void> deleteGameFromLibrary(@PathVariable String gameId, @PathVariable String username) {
        System.out.println("Intentant eliminar el joc " + gameId + " de l'usuari " + username);
        try {
            libraryLogic.deleteGameFromLibrary(gameId, username);
            log.info("Joc eliminat correctament.");
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            log.error("Joc no trobat a la llibreria");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error eliminant joc de la llibreria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obté la mitjana de puntuacions d'un joc.
     *
     * @param gameId L'ID del joc.
     * @return La mitjana de puntuacions del joc.
     */
    @GetMapping("/averagerating/{gameId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable String gameId) {
        log.info("Rebent petició per a la mitjana de puntuacions del videojoc " + gameId);
        Double rating = libraryLogic.getAverageRating(gameId);
        log.info("Mitjana de puntuacions: " + rating);
        return ResponseEntity.ok(rating);
    }

    /**
     * Gestiona la verificació d'una entrada a la biblioteca d'un usuari per a un
     * videojoc específic.
     *
     * @param gameId   Identificador del videojoc a verificar.
     * @param username Nom d'usuari del propietari de la biblioteca.
     * @return ResponseEntity que conté l'entrada de la biblioteca si existeix, o un
     *         estat 404 si no es troba.
     */
    @GetMapping("/verify/{gameId}/{username}")
    public ResponseEntity<Library> getLibraryEntry(@PathVariable String gameId, @PathVariable String username) {

        log.info("Rebent petició per a la llibreria de l'usuari " + username + " pel videojoc " + gameId);

        Optional<Library> libraryEntry = libraryLogic.getLibraryEntry(gameId, username);

        if (libraryEntry.isPresent()) {
            log.info("Entrada trobada: " + libraryEntry.get());
            return ResponseEntity.ok(libraryEntry.get());
        } else {
            log.warn("Entrada no trobada per aquest usuari i joc");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualitza una entrada existent a la biblioteca d'un usuari.
     *
     * @param library Objecte Library que conté la informació actualitzada.
     * @return ResponseEntity amb l'entrada actualitzada si l'operació té èxit,
     *         o un estat 500 en cas d'error durant l'actualització.
     */
    @PutMapping("/update")
    public ResponseEntity<Library> updateGameInLibrary(@RequestBody Library library) {
        log.info("📩 Rebuda petició d'actualització per Library ID: " + library.getIdLibrary());
        try {
            Library updatedLibrary = libraryLogic.updateGameInLibrary(library);
            log.info("Entrada actualitzada correctament: " + updatedLibrary);
            return ResponseEntity.ok(updatedLibrary);
        } catch (Exception e) {
            log.error("❌ Error actualitzant la biblioteca: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Compta el nombre de jocs en una categoria específica per a un usuari.
     *
     * @param username El nom d'usuari.
     * @param category La categoria dels jocs.
     * @return El nombre de jocs en la categoria especificada.
     */
    @GetMapping("/count/{username}/{category}")
    public ResponseEntity<?> countByCategory(@PathVariable String username, @PathVariable String category) {
        try {
            return ResponseEntity.ok(libraryLogic.countByCategory(username, category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}