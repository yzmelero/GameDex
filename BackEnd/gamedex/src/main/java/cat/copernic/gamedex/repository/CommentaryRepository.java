package cat.copernic.gamedex.repository;

import cat.copernic.gamedex.entity.Commentary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentaryRepository extends MongoRepository<Commentary, String>{

}
