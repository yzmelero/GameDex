package cat.copernic.gamedex.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Classe que representa una resposta d'inici de sessió.
 */
@Document(collection = "loginResponse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    /**
     * El nom d'usuari de la resposta d'inici de sessió.
     */
    private String username;

    /**
     * El tipus d'usuari de la resposta d'inici de sessió.
     */
    private UserType userType;
}