package cat.copernic.gamedex.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    String username;

    String password;

    String name;

    String surname;

    String email;

    int telephone;

    LocalDate birthDate;

    Binary[] profilePicture;

    // 0 for disabled or non validated users, 1 for validated users
    Boolean state;

    UserType userType;
}
