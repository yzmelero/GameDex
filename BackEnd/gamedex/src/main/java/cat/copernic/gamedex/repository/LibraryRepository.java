package cat.copernic.gamedex.repository;

import cat.copernic.gamedex.entity.Library;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface LibraryRepository extends MongoRepository<Library, String> {

    public List<Library> findAllByVideogame();

    @Query("{ 'user.username' : :#{#username}, 'videogame.gameId' : :#{#gameId} }")
    Optional<Library> findByUserAndVideogame(@Param("username") String username,
                                             @Param("gameId") String gameId);

    List<Library> findByUserUsername(String username);
}
