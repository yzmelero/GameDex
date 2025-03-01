package cat.copernic.gamedex.logic;

import cat.copernic.gamedex.entity.Library;
import cat.copernic.gamedex.repository.LibraryRepository;

import java.util.ArrayList;
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

    public List<Library> getLibraryByUser(String username){
       return libraryRepository.findByUserUsername(username);
    }
    Logger log = LoggerFactory.getLogger(LibraryLogic.class);
   
    
    public void delete(String idLibrary){
        try{
            Optional<Library> library = libraryRepository.findById(idLibrary);
            if (library.isPresent()) {
                libraryRepository.deleteById(idLibrary);
            }else{
                throw new RuntimeException("Library not found");
            } 
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException("Unexpected error while deleting library");
        }
        
    }
    
    public Library addGameToLibrary(Library library){
        System.out.println("Usuari rebut en backend: " + library.getUser().getUsername());
        Optional<Library> existingGamesInLibrary = libraryRepository.findByUserAndVideogame(
            library.getUser().getUsername(),
            library.getVideogame().getGameId()
        );
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

    public int countByCategory(String username, String state){
        return libraryRepository.countByUserUsernameAndState(username, state);
    }
}
