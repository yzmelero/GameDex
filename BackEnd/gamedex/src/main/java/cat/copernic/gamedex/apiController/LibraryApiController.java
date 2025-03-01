package cat.copernic.gamedex.apiController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cat.copernic.gamedex.logic.LibraryLogic;
import cat.copernic.gamedex.entity.Library;

@RestController
@RequestMapping("/api/library")
@CrossOrigin(origins = "*")
public class LibraryApiController {
 
    @Autowired
    private LibraryLogic libraryLogic;


    Logger log = LoggerFactory.getLogger(LibraryApiController.class);

    @PostMapping("/add")
    public ResponseEntity<?> addGameToLibrary(@RequestBody Library library) {
        System.out.println("Intentando añadir juego con ID: " + library.getVideogame().getGameId() + " para usuario: " + library.getUser().getUsername());

        
        try{
            Library newGameToLibrary = libraryLogic.addGameToLibrary(library);
            return ResponseEntity.status(HttpStatus.CREATED).body(newGameToLibrary);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Game already in library");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();        }
    }

    @GetMapping("/get")
    public List<Library> getLibrary(@RequestParam String username){
        return libraryLogic.getLibraryByUser(username);
    }

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

}
