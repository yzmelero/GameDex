package cat.copernic.gamedex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuració de seguretat per a l'aplicació.
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean per a l'encoder de contrasenyes BCrypt.
     *
     * @return Una instància de BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}