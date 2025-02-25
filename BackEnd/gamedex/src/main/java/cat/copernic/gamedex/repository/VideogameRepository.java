package cat.copernic.gamedex.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cat.copernic.gamedex.entity.Videogame;

@Repository
public interface VideogameRepository extends MongoRepository<Videogame, String> {
    
    public Optional<Videogame> findByNameGame(String nameGame);

}
