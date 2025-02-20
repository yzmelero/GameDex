package cat.copernic.gamedex.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cat.copernic.gamedex.entity.User;
import cat.copernic.gamedex.entity.LoginRequest;
import cat.copernic.gamedex.entity.LoginResponse;
import cat.copernic.gamedex.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
@Controller
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginApiController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
            
        }

        if (!user.getState()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not validated");

        }

        LoginResponse response = new LoginResponse(
                user.getUsername(),
                user.getUserType()
                );

        return ResponseEntity.ok(response);
    }
}
