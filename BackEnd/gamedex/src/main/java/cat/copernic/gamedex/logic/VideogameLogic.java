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

    // Rep un objecte Videogame i el guarda a la base de dades
    public Videogame createVideogame(Videogame videogame) {
        try {
            // Si gameID és null o está buit, MongoDB genera un ID
            if (videogame.getGameId() == null || videogame.getGameId().isEmpty()) {
                videogame.setGameId(null); // Null perque MongoDB generi un ID

                // Valida que no hi hagi camps buits
                if (videogame.getNameGame().isEmpty() || videogame.getDescriptionGame().isEmpty() ||
                        videogame.getReleaseYear() == 0 || videogame.getAgeRecommendation() == 0 ||
                        videogame.getDeveloper().isEmpty() || videogame.getGamePhoto() == null) {
                    throw new RuntimeException("Empty fields are not allowed");
                }
                if (videogame.getCategory() == null) {
                    throw new RuntimeException("Category is required");
                }
                // Comprobar si existeix un videojoc amb el mateix nom
                // Optional evita els null
                Optional<Videogame> existingGame = videogameRepo.findByNameGame(videogame.getNameGame());
                if (existingGame.isPresent()) {
                    throw new RuntimeException("Videogame with this name already exists");
                }
                // Estableix el camp state a false per defecte (no validat)
                videogame.setState(false);
            }
            return videogameRepo.save(videogame); // Guarda el joc a MongoDB, genera ID si és null

        } catch (RuntimeException e) {
            throw e; // Si hi ha una exepció personalitzada, la llança
        } catch (Exception e) {
            // Si hi ha una exepció genèrica, llança una exepció amb un missatge personalitzat
            throw new RuntimeException("Unexpected error creating videogame");
        }

    }

    // Rep un objecte Videogame i el modifica a la base de dades
    public Videogame modifyVideogame(Videogame videogame) {
        try {
            // Comprova si el videojoc existeix
            Optional<Videogame> oldVideogame = videogameRepo.findById(videogame.getGameId());
            if (oldVideogame.isEmpty()) {
                throw new RuntimeException("Videogame not found");
            } else {
                // Obtenir el videojoc de la base de dades
                Videogame newVideogame = oldVideogame.get();

                // Comprova si hi ha canvis en els camps
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

                // Guarda el videojoc modificat la base de dades
                return videogameRepo.save(newVideogame);

            }
        } catch (RuntimeException e) {
            throw e; 
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying videogame");
        }
    }

    // Rep un ID de videojoc i l'elimina de la base de dades
    public void deleteVideogame(String gameId) {
        try {
            // Comprova si el videojoc existeix
            Optional<Videogame> videogame = videogameRepo.findById(gameId);
            if (videogame.isEmpty()) {
                throw new RuntimeException("Videogame is not found");
            }
            // Elimina el videojoc de la base de dades
            videogameRepo.deleteById(gameId);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpecting error deleting videogame");
        }

    }

    // Retorna tots els videojocs de la base de dades
    public List<Videogame> getAllVideogames() {
        try {
            return videogameRepo.findByState(true);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all videogames");
        }
    }

    // Retorna tots els videojocs no validats de la base de dades
     public List<Videogame> getInactiveVideogames() {
        try {
            return videogameRepo.findByState(false);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting inactive videogames");
        }
    }

    // Rep un ID de videojoc i retorna el videojoc de la base de dades
    public Videogame getVideogameById(String gameId) {
        try {
            // Comprova si el videojoc existeix
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

    public Videogame validateVideogame (String gameId) {
        try {
            // Comprova si el videojoc existeix
            Optional<Videogame> videogame = videogameRepo.findById(gameId);
            if (videogame.isEmpty()) {
                throw new RuntimeException("Videogame not found");
            } else {
                Videogame newVideogame = videogame.get();
                newVideogame.setState(true);
                return videogameRepo.save(newVideogame);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error validating the videogame");
        }
    }

    public List<Videogame> getVideogameByCategory(String categoryId) {
        try {
            return videogameRepo.findByCategory(categoryId);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting videogames by category");
        }
    }

    public List<Videogame> searchVideogamesByName(String nameGame) {
        try {
            return videogameRepo.findByNameGameContaining(nameGame);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting videogames by name");
        }
    }
}
