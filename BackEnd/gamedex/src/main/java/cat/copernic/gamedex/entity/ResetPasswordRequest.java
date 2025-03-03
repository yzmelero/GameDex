package cat.copernic.gamedex.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Classe que representa una sol·licitud de restabliment de contrasenya.
 */
@Document(collection = "resetPasswordRequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    /**
     * El nom d'usuari per a la sol·licitud de restabliment de contrasenya.
     */
    private String username;

    /**
     * El correu electrònic associat a la sol·licitud de restabliment de
     * contrasenya.
     */
    private String email;
}