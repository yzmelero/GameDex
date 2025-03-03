package cat.copernic.gamedex.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Classe que representa una sol·licitud d'inici de sessió.
 */
@Document(collection = "loginRequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * El nom d'usuari per a l'inici de sessió.
     */
    private String username;

    /**
     * La contrasenya per a l'inici de sessió.
     */
    private String password;

    /**
     * Obté el nom d'usuari.
     *
     * @return El nom d'usuari.
     */
    public String getUsername() { return username; }

    /**
     * Obté la contrasenya.
     *
     * @return La contrasenya.
     */
    public String getPassword() { return password; }
}