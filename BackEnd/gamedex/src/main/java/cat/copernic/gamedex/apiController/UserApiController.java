package cat.copernic.gamedex.apiController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public List<User> getAllUsers() {

        return userLogic.getAllUsers();

    }

    @GetMapping("/byId/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userLogic.getUserById(userId);
    }

    @PutMapping("/update")
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
    public void deleteUser(@PathVariable String userId) {
        userLogic.deleteUser(userId);
    }
}
