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
import cat.copernic.gamedex.repository.UserRepository;

/**
 * Lògica de negoci per gestionar els usuaris.
 */
@Service
public class UserLogic {

    Logger log = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Valida els camps d'un usuari.
     *
     * @param user L'usuari a validar.
     */
    public void validations(User user) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!user.getEmail().matches(emailRegex)) {
            throw new RuntimeException("Invalid email format");
        }
        String phoneRegex = "^[0-9]{9}$";
        if (!String.valueOf(user.getTelephone()).matches(phoneRegex)) {
            throw new RuntimeException("Invalid telephone format");
        }
    }

    /**
     * Crea un nou usuari.
     *
     * @param user L'usuari a crear.
     * @return L'usuari creat.
     */
    public User createUser(User user) {
        try {
            Optional<User> oldUser = userRepository.findById(user.getUsername());
            if (oldUser.isPresent()) {
                throw new RuntimeException("User already exists");
            }
            if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty()
                    || user.getSurname().isEmpty() || user.getEmail().isEmpty() || user.getTelephone() == 0
                    || user.getBirthDate() == null) {
                throw new RuntimeException("Empty fields are not allowed");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            if (userRepository.findByTelephone(user.getTelephone()).isPresent()) {
                throw new RuntimeException("Telephone already exists");
            }
            validations(user);

            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating user", e);
        }
    }

    /**
     * Modifica un usuari existent.
     *
     * @param user L'usuari amb les dades actualitzades.
     * @return L'usuari modificat.
     */
    public User modifyUser(User user) {
        try {
            Optional<User> oldUser = userRepository.findById(user.getUsername());
            if (oldUser.isEmpty()) {
                throw new RuntimeException("User not found");
            } else {
                User newUser = oldUser.get();

                if ((user.getPassword() != newUser.getPassword()) && !user.getPassword().isEmpty()
                        && !user.getPassword().isBlank()) {
                    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
                }

                if ((user.getName() != newUser.getName()) && !user.getName().isEmpty()) {
                    newUser.setName(user.getName());
                }

                if ((user.getSurname() != newUser.getSurname()) && !user.getSurname().isEmpty()) {
                    newUser.setSurname(user.getSurname());
                }

                if ((!user.getEmail().equals(newUser.getEmail())) && !user.getEmail().isEmpty()) {
                    Optional<User> emailUser = userRepository.findByEmail(user.getEmail());
                    if (emailUser.isPresent() && !emailUser.get().getUsername().equals(user.getUsername())) {
                        throw new RuntimeException("Email already exists");
                    }
                    newUser.setEmail(user.getEmail());
                }

                if ((user.getTelephone() != newUser.getTelephone()) && user.getTelephone() != 0) {
                    Optional<User> telephoneUser = userRepository.findByTelephone(user.getTelephone());
                    if (telephoneUser.isPresent() && !telephoneUser.get().getUsername().equals(user.getUsername())) {
                        throw new RuntimeException("Telephone already exists");
                    }
                    newUser.setTelephone(user.getTelephone());
                }

                if ((user.getBirthDate() != newUser.getBirthDate()) && user.getBirthDate() != null) {
                    newUser.setBirthDate(user.getBirthDate());
                }

                if (user.getProfilePicture() != null) {
                    if (user.getProfilePicture() != newUser.getProfilePicture()) {
                        newUser.setProfilePicture(user.getProfilePicture());
                    }
                }

                validations(user);

                return userRepository.save(newUser);
            }
        } catch (RuntimeException e) {
            throw e; // Re-throw the original RuntimeException
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error modifying user");
        }
    }

    /**
     * Elimina un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     */
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

    /**
     * Obté tots els usuaris.
     *
     * @return Una llista de tots els usuaris.
     */
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (RuntimeException e) {
            throw e; // Re-throw the original RuntimeException
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting all users");
        }
    }

    /**
     * Obté tots els usuaris inactius.
     *
     * @return Una llista de tots els usuaris inactius.
     */
    public List<User> getInactiveUsers() {
        try {
            log.info("Getting inactive users");
            return userRepository.findByState(false);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting inactive users");
        }
    }

    /**
     * Obté un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return L'usuari amb l'ID especificat.
     */
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Obté usuaris pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return Una llista d'usuaris que coincideixen amb el nom d'usuari.
     */
    public List<User> getUserByUsername(String username) {
        try {
            return userRepository.findByUsernameContaining(username);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error getting user by username");
        }
    }

    /**
     * Valida un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari a validar.
     * @return L'usuari validat.
     */
    public User validateUser(String userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            User user = userOptional.get();
            user.setState(true);
            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error validating user");
        }
    }

    /**
     * Comprova si un usuari existeix pel seu nom d'usuari i correu electrònic.
     *
     * @param username El nom d'usuari.
     * @param email    El correu electrònic.
     * @return Cert si l'usuari existeix, fals en cas contrari.
     */
    public boolean userExists(String username, String email) {
        return userRepository.findByUsernameAndEmail(username, email).isPresent();
    }

    /**
     * Actualitza la contrasenya d'un usuari.
     *
     * @param username El nom d'usuari.
     * @param email    El correu electrònic.
     * @return Cert si la contrasenya s'ha actualitzat correctament, fals en cas
     *         contrari.
     */
    public boolean updatePassword(String username, String email) {
        Optional<User> userFound = userRepository.findByUsername(username);
        User user = userFound.get();
        if (user != null) {
            String newPassword = "1234";
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}