
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

    public Library addGameToLibrary(Library library) {

        try {
            Optional<Library> oldLibrary = libraryRepository.findById(library.getIdLibrary());
            if (oldLibrary.isPresent()) {
                throw new RuntimeException("Library already exists");
            }
            return libraryRepository.save(library);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException("Unexpected error while creating library");
        }
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
    
    public List<Library> getAllLibraries(){
        try{
           return libraryRepository.findAllByVideogame();
        }catch(Exception e){
            throw new RuntimeException("Unexpected error while listing libraries");
        }
    }
}

