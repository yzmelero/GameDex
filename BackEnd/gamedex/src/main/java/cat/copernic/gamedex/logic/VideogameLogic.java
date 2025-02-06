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

    public Videogame modifyVideogame(Videogame videogame) {
        try {
            Optional<Videogame> oldVideogame = videogameRepo.findById(videogame.getGameId());
            if (oldVideogame.isEmpty()) {
                throw new RuntimeException("Videogame not found");
            } else {
                Videogame newVideogame = oldVideogame.get();

                if (videogame.getNameGame() != newVideogame.getNameGame()) {
                    newVideogame.setNameGame(videogame.getNameGame());
                }

                if (videogame.getDescriptionGame() != newVideogame.getDescriptionGame()) {
                    newVideogame.setDescriptionGame(videogame.getDescriptionGame());
                }

                if (videogame.getReleaseYear() != newVideogame.getReleaseYear()) {
                    newVideogame.setReleaseYear(videogame.getReleaseYear());
                }

                if (videogame.getGamePhoto() != newVideogame.getGamePhoto()) {
                    newVideogame.setGamePhoto(videogame.getGamePhoto());
                }

                if (videogame.getAgeRecomendation() != newVideogame.getAgeRecomendation()) {
                    newVideogame.setAgeRecomendation(videogame.getAgeRecomendation());
                }

                if (videogame.getDeveloper() != newVideogame.getDeveloper()) {
                    newVideogame.setDeveloper(videogame.getDeveloper());
                }

                if (videogame.getNameCategory() != newVideogame.getNameCategory()) {
                    newVideogame.setNameCategory(videogame.getNameCategory());
                }

                return videogameRepo.save(newVideogame);

            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying videogame");
        }
    }

    public void deleteVideogame (String gameId) {
        try {
            Optional<Videogame> videogame = videogameRepo.findById(gameId);
            if (videogame.isEmpty()) {
                throw new RuntimeException("Videogame is not found");
            }
            videogameRepo.deleteById(gameId);
        } catch (Exception e) {
            throw new RuntimeException("Unexpecting error deleting videogame")
        }

    }
}
