package cat.copernic.gamedex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cat.copernic.gamedex.entity.Videogame;

@Repository
// Interfície que hereta de MongoRepository, que permet interactuar amb la base de dades
public interface VideogameRepository extends MongoRepository<Videogame, String> {
    
    // Mètodes per buscar videojocs per nom i per estat
    Optional<Videogame> findByNameGame(String nameGame);
    // Mètode per buscar videojocs per estat
    List<Videogame> findByState(boolean state);

}
