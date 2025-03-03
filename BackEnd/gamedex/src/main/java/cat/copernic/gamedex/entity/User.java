package cat.copernic.gamedex.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa un usuari.
 */
@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * El nom d'usuari.
     */
    @Id
    private String username;

    /**
     * La contrasenya de l'usuari.
     */
    private String password;

    /**
     * El nom de l'usuari.
     */
    private String name;

    /**
     * El cognom de l'usuari.
     */
    private String surname;

    /**
     * El correu electrònic de l'usuari.
     */
    private String email;

    /**
     * El telèfon de l'usuari.
     */
    private int telephone;

    /**
     * La data de naixement de l'usuari.
     */
    @Field(name = "birth_date")
    private LocalDate birthDate;

    /**
     * La foto de perfil de l'usuari en format byte array.
     */
    @Field(name = "profile_picture")
    private byte[] profilePicture;

    /**
     * L'estat de l'usuari (0 per a deshabilitat o no validat, 1 per a validat).
     */
    private Boolean state;

    /**
     * El tipus d'usuari.
     */
    @Field(name = "user_type")
    private UserType userType;
}