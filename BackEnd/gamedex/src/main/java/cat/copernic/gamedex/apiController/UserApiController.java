package cat.copernic.gamedex.apiController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cat.copernic.gamedex.entity.ResetPasswordRequest;
import cat.copernic.gamedex.entity.User;
import cat.copernic.gamedex.logic.UserLogic;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserApiController {

    Logger log = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserLogic userLogic;

    @GetMapping("/all/{userId}")
    public List<User> getAllUsersbyUsername(@PathVariable String userId) {
        if (userId.isEmpty() || userId == null) {
            return userLogic.getAllUsers();
        } else {
            return userLogic.getUserByUsername(userId);
        }
    }

    @GetMapping("/all/inactive")
    public List<User> getInactiveUsers() {
        log.info("Getting inactive users");
        return userLogic.getInactiveUsers();
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {

        return userLogic.getAllUsers();

    }

    @GetMapping("/view/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userLogic.getUserById(userId);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@RequestBody User user) {
        return userLogic.modifyUser(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating user: " + user.toString());
        User newUser = userLogic.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        log.info("Deleting user: " + userId);
        userLogic.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/validate/{userId}")
    public ResponseEntity<User> validateUser(@PathVariable String userId) {
        log.info("Validating user: " + userId);
        User validatedUser = userLogic.validateUser(userId);
        return ResponseEntity.ok(validatedUser);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Map<String,String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean isValidUser = userLogic.userExists(request.getUsername(), request.getEmail());
        Map<String, String> response = new HashMap<>();

        if (isValidUser) {

            userLogic.updatePassword(request.getUsername(), request.getEmail());
            response.put("message", "A default password has been created. IMPORTANT: Change it as soon as you login. Password: 1234");
            return ResponseEntity.ok(response);

        } else {

            response.put("error", "User not found. Please check the username and email.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        }

    }
}
