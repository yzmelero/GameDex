package cat.copernic.gamedex.logic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.copernic.gamedex.entity.Videogame;
import cat.copernic.gamedex.repository.VideogameRepository;

@Service
public class VideogameLogic {

    @Autowired
    private VideogameRepository videogameRepo;

    public Videogame createVideogame(Videogame videogame) {

        try {
            Optional<Videogame> oldVideogame = videogameRepo.findById(videogame.getGameId());
            if (oldVideogame.isPresent()) {
                throw new RuntimeException("Videogame already exists");
            }
            return videogameRepo.save(videogame);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating videogame");
        }

    }
}
