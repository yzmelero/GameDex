package cat.copernic.gamedex.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa un videojoc.
 */
@Document(collection = "videogame")
@Data // Lombok genera els getters i setters
@AllArgsConstructor // Lombok genera un constructor amb tots els atributs
@NoArgsConstructor // Lombok genera un constructor buit
public class Videogame {

    /**
     * L'identificador del videojoc.
     */
    @Id
    private String gameId;

    /**
     * El nom del videojoc.
     */
    @Field(name = "name_game")
    private String nameGame;

    /**
     * La descripció del videojoc.
     */
    @Field(name = "description_game")
    private String descriptionGame;

    /**
     * L'any de llançament del videojoc.
     */
    @Field(name = "release_year")
    private int releaseYear;

    /**
     * La foto del videojoc en format byte array.
     */
    @Field(name = "game_photo")
    private byte[] gamePhoto;

    /**
     * La recomanació d'edat per al videojoc.
     */
    @Field(name = "age_recommendation")
    private int ageRecommendation;

    /**
     * El desenvolupador del videojoc.
     */
    private String developer;

    /**
     * El nom de la categoria del videojoc.
     */
    @Field(name = "name_category")
    private String category; // Atribut per guardar només el nom de la categoria

    /**
     * L'estat del videojoc (0 per a no validat, 1 per a validat).
     */
    private Boolean state;
}