package cat.copernic.gamedex.apiController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.copernic.gamedex.logic.LibraryLogic;
import cat.copernic.gamedex.logic.VideogameLogic;
import cat.copernic.gamedex.entity.Library;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class LibraryApiController {
 
    @Autowired
    private LibraryLogic libraryLogic;

    Logger log = LoggerFactory.getLogger(LibraryApiController.class);

    @PostMapping("/add")
    public ResponseEntity<?> addGameToLibrary(@RequestBody Library library) {
        log.info("Adding game to library: " + library.toString());
        
        try{
            Library newGameToLibrary = libraryLogic.addGameToLibrary(library);
            return ResponseEntity.status(HttpStatus.CREATED).body(newGameToLibrary);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Game already in library");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();        }
    }
}
