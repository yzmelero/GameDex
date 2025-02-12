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

@SpringBootApplication
public class GamedexApplication {
	private static final Logger logger = LoggerFactory.getLogger(GamedexApplication.class);

	@Autowired
	private static UserRepository userRepository;

	@Autowired
	private static UserLogic userLogic;

	public static void main(String[] args) {
		logger.info("Starting GamedexApplication...");

		var context = SpringApplication.run(GamedexApplication.class, args);
		userRepository = context.getBean(UserRepository.class);
		userLogic = context.getBean(UserLogic.class);

		User user = new User("user", "user", "usuari", "apellido", "user@gmail.com", 12345678,
				LocalDate.of(1990, 1, 1), null, false, UserType.USER);
		if (userRepository.findById(user.getUsername()).isEmpty()) {
			userLogic.createUser(user);
		}

		// Crear administrador de ejemplo
		User admin = new User("admin", "admin", "admin", "apellido", "admin@gmail.com", 87654321,
				LocalDate.of(1985, 1, 1), null, true, UserType.ADMIN);
		if (userRepository.findById(admin.getUsername()).isEmpty()) {
			userLogic.createAdmin(admin);
		}

		logger.info("GamedexApplication started successfully.");
	}

}
