package cat.copernic.gamedex.logic;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cat.copernic.gamedex.apiController.UserApiController;
import cat.copernic.gamedex.entity.User;
import cat.copernic.gamedex.entity.UserType;
import cat.copernic.gamedex.repository.UserRepository;

@Service
public class UserLogic {
    
    Logger log = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) {
        try {
            Optional<User> oldUser = userRepository.findById(user.getUsername());
            if (oldUser.isPresent()) {
                throw new RuntimeException("User already exists");
            }
            if (user.getUsername().isEmpty() || user.getPassword().isEmpty() ||
                user.getName().isEmpty() || user.getSurname().isEmpty() ||
                user.getEmail().isEmpty() || user.getTelephone() == 0 ||
                user.getBirthDate() == null) {
                throw new RuntimeException("Empty fields are not allowed");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //Estas dos lineas hacen que el usuario creado por defecto sea un usuario normal y no un admin y que este desactivado.
            /*user.setState(false);
            user.setUserType(UserType.USER);*/

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            if (userRepository.findByTelephone(user.getTelephone()).isPresent()) {
                throw new RuntimeException("Telephone already exists");   
            }

            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating user", e);
        }
    }

    /*public User createAdmin(User user) {
        try {
            Optional<User> oldUser = userRepository.findById(user.getUsername());
            if (oldUser.isPresent()) {
                throw new RuntimeException("User already exists");
            }

            // Estas dos lineas hacen que el usuario creado por defecto sea un usuario
            // normal y no un admin y que este desactivado.
            user.setState(true);
            user.setUserType(UserType.ADMIN);

            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw e; // Re-throw the original RuntimeException
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating user");
        }
    }*/

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

                if (!user.getEmail().equals(newUser.getEmail())) {
                    Optional<User> emailUser = userRepository.findByEmail(user.getEmail());
                    if (emailUser.isPresent() && !emailUser.get().getUsername().equals(user.getUsername())) {
                        throw new RuntimeException("Email already exists");
                    }
                    newUser.setEmail(user.getEmail());
                }

                if (user.getTelephone() != newUser.getTelephone()) {
                    Optional<User> telephoneUser = userRepository.findByTelephone(user.getTelephone());
                    if (telephoneUser.isPresent() && !telephoneUser.get().getUsername().equals(user.getUsername())) {
                        throw new RuntimeException("Telephone already exists");
                    }
                    newUser.setTelephone(user.getTelephone());
                }

                if (user.getBirthDate() != newUser.getBirthDate()) {
                    newUser.setBirthDate(user.getBirthDate());
                }

                if (user.getProfilePicture() != newUser.getProfilePicture()) {
                    newUser.setProfilePicture(user.getProfilePicture());
                }


                return userRepository.save(newUser);
            }
        } catch (RuntimeException e) {
            throw e; // Re-throw the original RuntimeException
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
        } catch (RuntimeException e) {
            throw e; // Re-throw the original RuntimeException
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error deleting user");
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (RuntimeException e) {
            throw e; // Re-throw the original RuntimeException
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all users");
        }
    }

    public List<User> getInactiveUsers() {
        try {
            log.info("Getting inactive users");
            return userRepository.findByState(false);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting inactive users");
        }
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getUserByUsername(String username) {
        try {
            return userRepository.findByUsernameContaining(username);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting user by username");
        }
    }

    public User validateUser(String userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            User user = userOptional.get();
            user.setState(true);
            return userRepository.save(user);
        }catch (RuntimeException e) {
            throw e; 
        }catch (Exception e) {
            throw new RuntimeException("Unexpected error validating user");
        }        
    }

}
