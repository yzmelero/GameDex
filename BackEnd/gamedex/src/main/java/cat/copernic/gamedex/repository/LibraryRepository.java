package cat.copernic.gamedex.repository;

import cat.copernic.gamedex.entity.Library;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends MongoRepository<Library, String> {

    public List<Library> findAllByVideogame();

    @Query("{ 'videogame.gameId' : :#{#gameId}, 'user.username' : :#{#username} }")
    Optional<Library> findByUserAndVideogame(@Param("gameId") String gameId, 
                                             @Param("username") String username
                                             );

    public List<Library> findByUserUsername(String username);

    List<Library> findByVideogameGameId(String gameId);

}
