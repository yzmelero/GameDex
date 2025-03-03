package cat.copernic.gamedex.apiController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cat.copernic.gamedex.entity.Category;
import cat.copernic.gamedex.entity.Videogame;
import cat.copernic.gamedex.logic.CategoryLogic;
import cat.copernic.gamedex.logic.VideogameLogic;

/**
 * Controlador REST per gestionar els videojocs.
 */
@RestController // Indica que aquesta classe és un controlador REST
@RequestMapping("/api/videogame") // Defineix la URL base per a tots els mètodes
@CrossOrigin(origins = "*") // Permet l'accés a la API des de qualsevol origen
public class VideogameApiController {

    // Logger per mostrar missatges de seguiment dels endpoints
    Logger log = LoggerFactory.getLogger(VideogameApiController.class);

    @Autowired
    private VideogameLogic videogameLogic;

    @Autowired
    private CategoryLogic categoryLogic;

    /**
     * Crea un nou videojoc.
     *
     * @param videogame El videojoc a crear.
     * @return El videojoc creat.
     */
    @PostMapping("/create") // Defineix una ruta POST per crear un videojoc
    public ResponseEntity<Videogame> createVideogame(@RequestBody Videogame videogame) {
        log.info("Creating videogame: " + videogame.toString());
        Videogame newVideogame = videogameLogic.createVideogame(videogame);
        // Retorna l'OK amb el joc creat
        return ResponseEntity.ok(newVideogame);
    }

    /**
     * Modifica un videojoc existent.
     *
     * @param videogame El videojoc amb les dades actualitzades.
     * @return El videojoc modificat.
     */
    @PutMapping("/update") // Defineix una ruta PUT per modificar un videojoc
    public ResponseEntity<Videogame> updateVideogame(@RequestBody Videogame videogame) {
        log.info("Updating videogame: " + videogame.toString());
        Videogame updatedVideogame = videogameLogic.modifyVideogame(videogame);
        // Retorna l'OK amb el joc modificat
        return ResponseEntity.ok(updatedVideogame);
    }

    /**
     * Elimina un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc a eliminar.
     * @return Una resposta sense contingut si l'eliminació és correcta.
     */
    @DeleteMapping("/delete/{gameId}") // Defineix una ruta DELETE per eliminar un videojoc
    public ResponseEntity<Void> deleteVideogame(@PathVariable String gameId) {
        log.info("Delete videogame with ID: " + gameId);
        if (gameId == null || gameId.isBlank()) {
            log.error("Error: gameId is null or empty!");
            // Ho retorna quan gameId és null o buit
            return ResponseEntity.badRequest().build();
        }
        videogameLogic.deleteVideogame(gameId);
        // Retorna l'OK sense cap contingut
        return ResponseEntity.ok().build();
    }

    /**
     * Obté tots els videojocs.
     *
     * @return Una llista de tots els videojocs.
     */
    @GetMapping("/all") // Defineix una ruta GET per obtenir tots els videojocs
    public ResponseEntity<List<Videogame>> getAllVideogames() {
        log.info("Getting all videogames");
        List<Videogame> videogames = videogameLogic.getAllVideogames();
        // Retorna l'OK amb la llista de videojocs
        return ResponseEntity.ok(videogames);
    }

    /**
     * Obté tots els videojocs inactius.
     *
     * @return Una llista de tots els videojocs inactius.
     */
    @GetMapping("/all/inactive") // Defineix una ruta GET per obtenir tots els videojocs inactius
    public ResponseEntity<List<Videogame>> getInactiveVideogames() {
        log.info("Getting inactive users");
        List<Videogame> videogames = videogameLogic.getInactiveVideogames();
        // Retorna l'OK amb la llista de videojocs inactius
        return ResponseEntity.ok(videogames);
    }

    /**
     * Obté un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return El videojoc amb l'ID especificat.
     */
    @GetMapping("/byId/{gameId}") // Defineix una ruta GET per obtenir un videojoc per ID
    public ResponseEntity<Videogame> getVideogameById(@PathVariable String gameId) {
        log.info("Getting videogame by id: " + gameId);
        Videogame videogame = videogameLogic.getVideogameById(gameId);
        // Retorna l'OK amb el videojoc trobat
        return ResponseEntity.ok(videogame);
    }

    /**
     * Obté totes les categories.
     *
     * @return Una llista de totes les categories.
     */
    @GetMapping("/categories") // Defineix una ruta GET per obtenir totes les categories
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Getting all categories");
        List<Category> categories = categoryLogic.getAllCategory();
        // Retorna l'OK amb la llista de categories
        return ResponseEntity.ok(categories);
    }

    /**
     * Valida un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc a validar.
     * @return El videojoc validat.
     */
    @PutMapping("/validate/{gameId}") // Defineix una ruta PUT per validar un videojoc
    public ResponseEntity<Videogame> validateVideogame(@PathVariable String gameId) {
        log.info("Validating videogame with ID: " + gameId);
        Videogame videogame = videogameLogic.validateVideogame(gameId);
        // Retorna l'OK amb el videojoc validat
        return ResponseEntity.ok(videogame);
    }
    
    @GetMapping("/byCategory/{category}") // Defineix una ruta GET per a obtenir tots els videojocs per categoria
    public ResponseEntity<List<Videogame>> getVideogamesByCategory(@PathVariable String category) {
        log.info("Getting videogames by category: " + category);
        List<Videogame> videogames = videogameLogic.getVideogameByCategory(category);
        // Retorna l'OK amb la llista de videojocs per categoria
        return ResponseEntity.ok(videogames);
    }

    @GetMapping("/byName/{nameGame}") // Defineix una ruta GET per a obtenir tots els videojocs per nom
    public ResponseEntity<List<Videogame>> getVideogamesByName(@PathVariable String nameGame) {
        log.info("Getting videogames by name: " + nameGame);
        List<Videogame> videogames = videogameLogic.searchVideogamesByName(nameGame);
        // Retorna l'OK amb la llista de videojocs per nom
        return ResponseEntity.ok(videogames);
    }
}
