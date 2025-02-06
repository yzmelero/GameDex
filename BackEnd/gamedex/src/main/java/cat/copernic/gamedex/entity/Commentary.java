package cat.copernic.gamedex.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "commentary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commentary {

    @Id
    @Field(name = "id_commentary")
    private String idCommentary;

    private StateType state;

    private String description;

    private double rating;

    @DBRef
    private User user;

    @DBRef
    private Videogame videogame;

    @Field(name = "published_date")
    private LocalDate publishedDate;
    }
