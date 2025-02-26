package cat.copernic.gamedex.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Document(collection = "resetPasswordRequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    
    private String username;
    private String email;
}
