package cat.copernic.gamedex.repository;

import cat.copernic.gamedex.entity.Library;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibraryRepository extends MongoRepository<Library, String> {

    public List<Library> findAllByVideogame();

}
