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

@RestController // Indica que aquesta classe és un controlador REST
@RequestMapping("/api/videogame") // Defineix la URL base per a tots els mètodes
@CrossOrigin(origins = "*") // Permet l'accés a la API des de qualsevol origen
public class VideogameApiController {

    // Logger per a mostrar missatges de seguiment dels endpoints
    Logger log = LoggerFactory.getLogger(VideogameApiController.class);

    @Autowired
    private VideogameLogic videogameLogic; 

    @Autowired
    private CategoryLogic categoryLogic;

    @PostMapping("/create") // Defineix una ruta POST per a crear un videojoc
    // @RequestBody rep el joc al del frontend i el converteix a un objecte Videogame
    // ResponseEntity és un objecte que conté la resposta HTTP i fa que les respostes siguin més flexibles donant més control
    public ResponseEntity <Videogame> createVideogame(@RequestBody Videogame videogame) {
        log.info("Creating videogame: " + videogame.toString());
        Videogame newVideogame = videogameLogic.createVideogame(videogame);
        // Retorna l'OK amb el joc creat
        return ResponseEntity.ok(newVideogame);
    }

    @PutMapping("/update") // Defineix una ruta PUT per a modificar un videojoc
    public ResponseEntity <Videogame> updateVideogame(@RequestBody Videogame videogame) {
        log.info("Updating videogame: " + videogame.toString());
        Videogame updatedVideogame = videogameLogic.modifyVideogame(videogame);
        // Retorna l'OK amb el joc modificat
        return ResponseEntity.ok(updatedVideogame);
    }

    @DeleteMapping("/delete/{gameId}") // Defineix una ruta DELETE per a eliminar un videojoc
    // @PathVariable rep el gameId de la URL i l'assigna com a paràmetre
    public ResponseEntity <Void> deleteVideogame(@PathVariable String gameId) {
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

    @GetMapping("/all") // Defineix una ruta GET per a obtenir tots els videojocs
    // Retorna una llista de videojocs
    public ResponseEntity <List<Videogame>> getAllVideogames() {
        log.info("Getting all videogames");
        List<Videogame> videogames = videogameLogic.getAllVideogames();
        // Retorna l'OK amb la llista de videojocs
        return ResponseEntity.ok(videogames);
    }

    @GetMapping("/all/inactive") // Defineix una ruta GET per a obtenir tots els videojocs inactius
    public ResponseEntity <List<Videogame>> getInactiveVideogames() {
        log.info("Getting inactive users");
        List<Videogame> videogames = videogameLogic.getInactiveVideogames();
        // Retorna l'OK amb la llista de videojocs inactius
        return ResponseEntity.ok(videogames);
    }

    @GetMapping("/byId/{gameId}") // Defineix una ruta GET per a obtenir un videojoc per ID
    public ResponseEntity <Videogame> getVideogameById(@PathVariable String gameId) {
        log.info("Getting videogame by id: " + gameId);
        Videogame videogame = videogameLogic.getVideogameById(gameId);
        // Retorna l'OK amb el videojoc trobat
        return ResponseEntity.ok(videogame);
    }

    @GetMapping("/categories") // Defineix una ruta GET per a obtenir totes les categories
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Getting all categories");
        List<Category> categories = categoryLogic.getAllCategory();
        // Retorna l'OK amb la llista de categories
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/validate/{gameId}") // Defineix una ruta PUT per a validar un videojoc
    public ResponseEntity <Videogame> validateVideogame(@PathVariable String gameId) {
        log.info("Validating videogame with ID: " + gameId);
        Videogame videogame = videogameLogic.validateVideogame(gameId);
        // Retorna l'OK amb el videojoc validat
        return ResponseEntity.ok(videogame);
    }
}
