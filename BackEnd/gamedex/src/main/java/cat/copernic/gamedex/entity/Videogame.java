package cat.copernic.gamedex.entity;

import java.time.LocalDate;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "videogame")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Videogame {
    
    @Id
    @Field(name = "game_id")
    private String gameId;

    @Field(name = "name_game")
    private String nameGame;

    @Field(name = "description_game")
    private String descriptionGame;

    @Field(name = "release_year")
    private LocalDate releaseYear;
    
    @Field(name = "game_photo")
    private Binary gamePhoto;

    @Field(name = "age_recomendation")
    private int ageRecomendation;

    private String developer;

    @Field(name = "name_category")
    private String nameCategory;

}
