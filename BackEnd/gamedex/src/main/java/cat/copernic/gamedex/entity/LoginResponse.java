package cat.copernic.gamedex.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection = "loginResponse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String username;
    private UserType userType;
    
}
