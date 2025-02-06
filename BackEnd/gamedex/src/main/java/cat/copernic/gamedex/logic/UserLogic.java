package cat.copernic.gamedex.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.copernic.gamedex.entity.User;
import cat.copernic.gamedex.entity.UserType;
import cat.copernic.gamedex.repository.UserRepository;

@Service
public class UserLogic {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        try {
            Optional<User> oldUser = userRepository.findById(user.getUsername());
            if (oldUser.isPresent()) {
                throw new RuntimeException("User already exists");
            }

            //Estas dos lineas hacen que el usuario creado por defecto sea un usuario normal y no un admin y que este desactivado.
            user.setState(false);
            user.setUserType(UserType.USER);

            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating user");
        }
    }

    public User createAdmin(User user){
        try {
            Optional<User> oldUser = userRepository.findById(user.getUsername());
            if (oldUser.isPresent()) {
                throw new RuntimeException("User already exists");
            }

            //Estas dos lineas hacen que el usuario creado por defecto sea un usuario normal y no un admin y que este desactivado.
            user.setState(true);
            user.setUserType(UserType.ADMIN);

            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating user");
        }
    }

    public User modifyUser(User user) {
        try {
            Optional<User> oldUser = userRepository.findById(user.getUsername());
            if (oldUser.isEmpty()) {
                throw new RuntimeException("User not found");
            } else {
                User newUser = oldUser.get();

                if (user.getPassword() != newUser.getPassword()) {
                    newUser.setPassword(user.getPassword());
                }

                if (user.getName() != newUser.getName()) {
                    newUser.setName(user.getName());
                }

                if (user.getSurname() != newUser.getSurname()) {
                    newUser.setSurname(user.getSurname());
                }

                if (user.getEmail() != newUser.getEmail()) {
                    newUser.setEmail(user.getEmail());
                }

                if (user.getTelephone() != newUser.getTelephone()) {
                    newUser.setTelephone(user.getTelephone());
                }

                if (user.getBirthDate() != newUser.getBirthDate()) {
                    newUser.setBirthDate(user.getBirthDate());
                }

                if (user.getProfilePicture() != newUser.getProfilePicture()) {
                    newUser.setProfilePicture(user.getProfilePicture());
                }

                if (user.getState() != newUser.getState()) {
                    newUser.setState(user.getState());
                }

                if (user.getUserType() != newUser.getUserType()) {
                    newUser.setUserType(user.getUserType());
                }

                return userRepository.save(newUser);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying user");

        }
    }

    public void deleteUser(String username) {
        try {
            Optional<User> user = userRepository.findById(username);
            if (user.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            userRepository.deleteById(username);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error deleting user");
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all users");
        }
    }

}
