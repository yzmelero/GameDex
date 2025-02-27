package cat.copernic.gamedex.entity;

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
    private String gameId;

    @Field(name = "name_game")
    private String nameGame;

    @Field(name = "description_game")
    private String descriptionGame;

    @Field(name = "release_year")
    private int releaseYear;
    
    @Field(name = "game_photo")
    private byte[] gamePhoto;

    @Field(name = "age_recommendation")
    private int ageRecommendation;

    private String developer;

    @Field(name = "name_category")
    private String category;

    // 0 for non validated games, 1 for validated games
    private Boolean state;

}
