package cat.copernic.gamedex.logic;

import java.util.List;
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
            // Si gameID és null o está buit, MongoDB genera un ID
            if (videogame.getGameId() == null || videogame.getGameId().isEmpty()) {
                videogame.setGameId(null); // Null perque MongoDB generi un ID

                if (videogame.getNameGame().isEmpty() || videogame.getDescriptionGame().isEmpty() ||
                        videogame.getReleaseYear() == 0 || videogame.getAgeRecommendation() == 0 ||
                        videogame.getDeveloper().isEmpty()) {
                    throw new RuntimeException("Empty fields are not allowed");
                }
                if (videogame.getCategory() == null) {
                    throw new RuntimeException("Category is required");
                }
                // Comprobar si existeix un videojoc amb el mateix nom
                Optional<Videogame> existingGame = videogameRepo.findByNameGame(videogame.getNameGame());
                if (existingGame.isPresent()) {
                    throw new RuntimeException("Videogame with this name already exists");
                }
            }
            return videogameRepo.save(videogame); // MongoDB genera ID si és null

        } catch (RuntimeException e) {
            throw e;
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

                if (videogame.getAgeRecommendation() != newVideogame.getAgeRecommendation()) {
                    newVideogame.setAgeRecommendation(videogame.getAgeRecommendation());
                }

                if (videogame.getDeveloper() != newVideogame.getDeveloper()) {
                    newVideogame.setDeveloper(videogame.getDeveloper());
                }

                if (videogame.getCategory() != newVideogame.getCategory()) {
                    newVideogame.setCategory(videogame.getCategory());
                }

                return videogameRepo.save(newVideogame);

            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying videogame");
        }
    }

    public void deleteVideogame(String gameId) {
        try {
            Optional<Videogame> videogame = videogameRepo.findById(gameId);
            if (videogame.isEmpty()) {
                throw new RuntimeException("Videogame is not found");
            }
            videogameRepo.deleteById(gameId);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpecting error deleting videogame");
        }

    }

    public List<Videogame> getAllVideogames() {
        try {
            return videogameRepo.findAll();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all videogames");
        }
    }

    public Videogame getVideogameById(String gameId) {
        try {
            Optional<Videogame> videogame = videogameRepo.findById(gameId);
            if (videogame.isEmpty()) {
                throw new RuntimeException("Videogame not found");
            } else {
                return videogame.get();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting the videogame");
        }
    }
}
