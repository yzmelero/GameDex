package cat.copernic.gamedex.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.copernic.gamedex.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
