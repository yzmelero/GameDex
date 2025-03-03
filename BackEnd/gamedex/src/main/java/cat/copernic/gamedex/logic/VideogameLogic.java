package cat.copernic.gamedex.logic;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.copernic.gamedex.entity.Videogame;
import cat.copernic.gamedex.repository.VideogameRepository;

/**
 * Lògica de negoci per gestionar els videojocs.
 */
@Service
public class VideogameLogic {

    @Autowired
    private VideogameRepository videogameRepo;

    /**
     * Crea un nou videojoc.
     *
     * @param videogame El videojoc a crear.
     * @return El videojoc creat.
     */
    public Videogame createVideogame(Videogame videogame) {
        try {
            // Si gameID és null o està buit, MongoDB genera un ID
            if (videogame.getGameId() == null || videogame.getGameId().isEmpty()) {
                videogame.setGameId(null); // Null perquè MongoDB generi un ID

                // Valida que no hi hagi camps buits
                if (videogame.getNameGame().isEmpty() || videogame.getDescriptionGame().isEmpty() ||
                        videogame.getReleaseYear() == 0 || videogame.getAgeRecommendation() == 0 ||
                        videogame.getDeveloper().isEmpty() || videogame.getGamePhoto() == null || videogame.getGamePhoto().length == 0) {
                    throw new RuntimeException("Empty fields are not allowed");
                }
                if (videogame.getCategory() == null) {
                    throw new RuntimeException("Category is required");
                }
                // Comprova si existeix un videojoc amb el mateix nom
                Optional<Videogame> existingGame = videogameRepo.findByNameGame(videogame.getNameGame());
                if (existingGame.isPresent()) {
                    throw new RuntimeException("Videogame with this name already exists");
                }
                // Estableix el camp state a false per defecte (no validat)
                videogame.setState(false);
            }
            return videogameRepo.save(videogame); // Guarda el joc a MongoDB, genera ID si és null

        } catch (RuntimeException e) {
            throw e; // Si hi ha una excepció personalitzada, la llança
        } catch (Exception e) {
            // Si hi ha una excepció genèrica, llança una excepció amb un missatge
            // personalitzat
            throw new RuntimeException("Unexpected error creating videogame");
        }
    }

    /**
     * Modifica un videojoc existent.
     *
     * @param videogame El videojoc amb les dades actualitzades.
     * @return El videojoc modificat.
     */
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
                    if (videogame.getReleaseYear() < 1950 || videogame.getReleaseYear() > 2023) {
                        throw new RuntimeException("Release year must be between 1950 and 2023");
                    }
                    newVideogame.setReleaseYear(videogame.getReleaseYear());
                }

                if (videogame.getGamePhoto() != null && !videogame.getGamePhoto().equals(newVideogame.getGamePhoto())) {
                    newVideogame.setGamePhoto(videogame.getGamePhoto());
                }

                if (videogame.getAgeRecommendation() != newVideogame.getAgeRecommendation()) {
                    if (videogame.getAgeRecommendation() < 0 || videogame.getAgeRecommendation() > 50) {
                        throw new RuntimeException("Age recommendation must be between 0 and 50");
                    }
                    newVideogame.setAgeRecommendation(videogame.getAgeRecommendation());
                }

                if (videogame.getDeveloper() != newVideogame.getDeveloper()) {
                    newVideogame.setDeveloper(videogame.getDeveloper());
                }

                if (videogame.getCategory() != newVideogame.getCategory()) {
                    newVideogame.setCategory(videogame.getCategory());
                }

                // Guarda el videojoc modificat a la base de dades
                return videogameRepo.save(newVideogame);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying videogame");
        }
    }

    /**
     * Elimina un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc a eliminar.
     */
    public void deleteVideogame(String gameId) {
        try {
            // Comprova si el videojoc existeix
            Optional<Videogame> videogame = videogameRepo.findById(gameId);
            if (videogame.isEmpty()) {
                throw new RuntimeException("Videogame not found");
            }
            // Elimina el videojoc de la base de dades
            videogameRepo.deleteById(gameId);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error deleting videogame");
        }
    }

    /**
     * Obté tots els videojocs.
     *
     * @return Una llista de tots els videojocs.
     */
    public List<Videogame> getAllVideogames() {
        try {
            return videogameRepo.findByState(true);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all videogames");
        }
    }

    /**
     * Obté tots els videojocs no validats.
     *
     * @return Una llista de tots els videojocs no validats.
     */
    public List<Videogame> getInactiveVideogames() {
        try {
            return videogameRepo.findByState(false);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting inactive videogames");
        }
    }

    /**
     * Obté un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return El videojoc amb l'ID especificat.
     */
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

    /**
     * Valida un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc a validar.
     * @return El videojoc validat.
     */
    public Videogame validateVideogame(String gameId) {
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
            return videogameRepo.findByCategoryAndState(categoryId, true);
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
