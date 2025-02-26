package cat.copernic.gamedex.logic;

import cat.copernic.gamedex.entity.Library;
import cat.copernic.gamedex.repository.LibraryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryLogic {

    @Autowired
    private LibraryRepository libraryRepository;

    public List<Library> getLibraryByUser(String username){
       return libraryRepository.findByUserUsername(username);
    }
   
    
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
        Optional<Library> existingGameInLibrary = libraryRepository.findByUserIdAndVideogameId(
            library.getUser().getUsername(),
            library.getVideogame().getGameId());

        if(existingGameInLibrary.isPresent()){
            throw new RuntimeException("Game already in library");
        }
        return libraryRepository.save(library);
    }
    /*public List<Library> getAllCommentaries(){
    public List<Library> getAllLibraries(){
        try{
            List<Library> libraries = libraryRepository.findAllByVideogame();
           return libraryRepository.findAllByVideogame();
        }catch(Exception e){
            throw new RuntimeException("Unexpected error while listing libraries");
        }
            throw new RuntimeException("Unexpected error while listing commentaries");
    }
    }*/
}

