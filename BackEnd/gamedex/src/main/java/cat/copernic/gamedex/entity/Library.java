package cat.copernic.gamedex.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "library")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    @Id
    @Field(name = "idLibrary")
    private String idLibrary;

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
