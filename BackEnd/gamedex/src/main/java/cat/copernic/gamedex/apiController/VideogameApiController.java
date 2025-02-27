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

@RestController
@RequestMapping("/api/videogame")
@CrossOrigin(origins = "*")
public class VideogameApiController {

    Logger log = LoggerFactory.getLogger(VideogameApiController.class);

    @Autowired
    private VideogameLogic videogameLogic;

    @Autowired
    private CategoryLogic categoryLogic;

    @PostMapping("/create")
    public ResponseEntity <Videogame> createVideogame(@RequestBody Videogame videogame) {
        log.info("Creating videogame: " + videogame.toString());
        Videogame newVideogame = videogameLogic.createVideogame(videogame);
        return ResponseEntity.ok(newVideogame);
    }

    @PutMapping("/update")
    public ResponseEntity <Videogame> updateVideogame(@RequestBody Videogame videogame) {
        log.info("Updating videogame: " + videogame.toString());
        Videogame updatedVideogame = videogameLogic.modifyVideogame(videogame);
        return ResponseEntity.ok(updatedVideogame);
    }

    @DeleteMapping("/delete/{gameId}")
    public ResponseEntity <Void> deleteVideogame(@PathVariable String gameId) {
        log.info("Delete videogame with ID: " + gameId);
        if (gameId == null || gameId.isBlank()) {
            log.error("Error: gameId is null or empty!");
            return ResponseEntity.badRequest().build();
        }
        videogameLogic.deleteVideogame(gameId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity <List<Videogame>> getAllVideogames() {
        log.info("Getting all videogames");
        List<Videogame> videogames = videogameLogic.getAllVideogames();
        return ResponseEntity.ok(videogames);
    }

    @GetMapping("/all/inactive")
    public ResponseEntity <List<Videogame>> getInactiveVideogames() {
        log.info("Getting inactive users");
        List<Videogame> videogames = videogameLogic.getInactiveVideogames();
        return ResponseEntity.ok(videogames);
    }

    @GetMapping("/byId/{gameId}")
    public ResponseEntity <Videogame> getVideogameById(@PathVariable String gameId) {
        log.info("Getting videogame by id: " + gameId);
        Videogame videogame = videogameLogic.getVideogameById(gameId);
        return ResponseEntity.ok(videogame);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Getting all categories");
        List<Category> categories = categoryLogic.getAllCategory();
        return ResponseEntity.ok(categories);
    }
}
