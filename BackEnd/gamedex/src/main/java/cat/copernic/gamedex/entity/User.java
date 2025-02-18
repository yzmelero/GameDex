package cat.copernic.gamedex.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String username;

    private String password;

    private String name;

    private String surname;

    private String email;

    private int telephone;

    @Field(name = "birth_date")
    private LocalDate birthDate;

    @Field(name = "profile_picture")
    private byte[] profilePicture;
    //private String profilePicture;

    // 0 for disabled or non validated users, 1 for validated users
    private Boolean state;

    @Field(name = "user_type")
    private UserType userType;
}
