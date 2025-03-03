package cat.copernic.gamedex;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cat.copernic.gamedex.entity.User;
import cat.copernic.gamedex.entity.UserType;
import cat.copernic.gamedex.logic.UserLogic;
import cat.copernic.gamedex.repository.UserRepository;

import java.time.LocalDate;

import org.slf4j.Logger;

/**
 * Classe principal de l'aplicació Gamedex.
 */
@SpringBootApplication
public class GamedexApplication {
    private static final Logger logger = LoggerFactory.getLogger(GamedexApplication.class);

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static UserLogic userLogic;

    /**
     * Mètode principal que inicia l'aplicació.
     *
     * @param args Arguments de la línia de comandes.
     */
    public static void main(String[] args) {
        logger.info("Starting GamedexApplication...");

        var context = SpringApplication.run(GamedexApplication.class, args);
        userRepository = context.getBean(UserRepository.class);
        userLogic = context.getBean(UserLogic.class);

        // Crear usuari d'exemple
        User user = new User("user", "user", "usuari", "apellido", "user@gmail.com", 
                876543224,
                LocalDate.of(1990, 1, 1), null, true, UserType.USER);
        if (userRepository.findById(user.getUsername()).isPresent()) {
        } else {
            userLogic.createUser(user);
        }

        // Crear administrador d'exemple
        User admin = new User("admin", "admin", "admin", "apellido", "admin@gmail.com", 876543221,
                LocalDate.of(1985, 1, 1), null, true, UserType.ADMIN);
        if (userRepository.findById(admin.getUsername()).isEmpty()) {
            userLogic.createUser(admin);
        }

        logger.info("GamedexApplication started successfully.");
    }
}