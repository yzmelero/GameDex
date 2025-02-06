

package cat.copernic.gamedex.repository;

import cat.copernic.gamedex.model.Commentary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface CommentaryRepository extends MongoRepository<Commentary, String>{

}
