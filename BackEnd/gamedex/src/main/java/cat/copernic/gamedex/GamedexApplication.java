package cat.copernic.gamedex;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
@SpringBootApplication
public class GamedexApplication {
  private static final Logger logger = LoggerFactory.getLogger(GamedexApplication.class);
	public static void main(String[] args) {
		logger.info("Starting GamedexApplication...");
		SpringApplication.run(GamedexApplication.class, args);
		logger.info("GamedexApplication started successfully.");
	}

}
