package cat.copernic.gamedex.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Classe que representa una biblioteca de videojocs.
 */
@Document(collection = "library")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    /**
     * L'identificador de la biblioteca.
     */
    @Id
    @Field(name = "idLibrary")
    private String idLibrary;

    /**
     * L'estat del videojoc a la biblioteca.
     */
    private StateType state;

    /**
     * La descripció del videojoc a la biblioteca.
     */
    private String description;

    /**
     * La puntuació del videojoc a la biblioteca.
     */
    private double rating;

    /**
     * L'usuari propietari de la biblioteca.
     */
    @DBRef
    private User user;

    /**
     * El videojoc a la biblioteca.
     */
    @DBRef
    private Videogame videogame;

    /**
     * La data de publicació del videojoc a la biblioteca.
     */
    @Field(name = "published_date")
    private LocalDate publishedDate;
}