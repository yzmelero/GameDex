package cat.copernic.gamedex.logic;

import org.springframework.stereotype.Service;
import cat.copernic.gamedex.entity.Videogame;
import cat.copernic.gamedex.repository.VideogameRepository;

@Service
public class VideogameLogic {
    
    VideogameRepository videogameRepo;

    public VideogameLogic(VideogameRepository videogameRepo) {
        this.videogameRepo = videogameRepo;
    }

    public String createVideogame(Videogame videogame){
        
        Videogame ret = videogameRepo.save(videogame);
        return ret.getGameId();
        
    }
}
