package cat.copernic.gamedex.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa una categoria de videojocs.
 */
@Document(collection = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    /**
     * El nom de la categoria.
     */
    @Id
    private String nameCategory;

    /**
     * La descripció de la categoria.
     */
    private String description;

    /**
     * La foto de la categoria en format byte array.
     */
    @Field(name = "category_photo")
    private byte[] categoryPhoto;
}