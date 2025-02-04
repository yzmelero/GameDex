package cat.copernic.gamedex.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import cat.copernic.gamedex.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

}
