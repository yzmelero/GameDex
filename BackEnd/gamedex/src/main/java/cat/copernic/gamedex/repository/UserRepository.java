package cat.copernic.gamedex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.copernic.gamedex.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUsernameContaining(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByTelephone(int telephone);

    List<User> findByState(boolean b);
}
