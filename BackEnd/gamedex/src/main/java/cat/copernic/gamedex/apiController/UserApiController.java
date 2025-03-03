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

/**
 * Controlador REST per gestionar els usuaris.
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserApiController {

    Logger log = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserLogic userLogic;

    /**
     * Obté tots els usuaris pel seu nom d'usuari.
     *
     * @param userId El nom d'usuari.
     * @return Una llista d'usuaris que coincideixen amb el nom d'usuari.
     */
    @GetMapping("/all/{userId}")
    public List<User> getAllUsersbyUsername(@PathVariable String userId) {
        if (userId.isEmpty() || userId == null) {
            return userLogic.getAllUsers();
        } else {
            return userLogic.getUserByUsername(userId);
        }
    }

    /**
     * Obté tots els usuaris inactius.
     *
     * @return Una llista d'usuaris inactius.
     */
    @GetMapping("/all/inactive")
    public List<User> getInactiveUsers() {
        log.info("Getting inactive users");
        return userLogic.getInactiveUsers();
    }

    /**
     * Obté tots els usuaris.
     *
     * @return Una llista de tots els usuaris.
     */
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userLogic.getAllUsers();
    }

    /**
     * Obté un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return L'usuari amb l'ID especificat.
     */
    @GetMapping("/view/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userLogic.getUserById(userId);
    }

    /**
     * Actualitza un usuari existent.
     *
     * @param user L'usuari amb les dades actualitzades.
     * @return L'usuari actualitzat.
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateduser = userLogic.modifyUser(user);
        return ResponseEntity.ok(updateduser);
    }

    /**
     * Crea un nou usuari.
     *
     * @param user L'usuari a crear.
     * @return L'usuari creat.
     */
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating user: " + user.toString());
        User newUser = userLogic.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    /**
     * Elimina un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari a eliminar.
     * @return Una resposta sense contingut si l'eliminació és correcta.
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        log.info("Deleting user: " + userId);
        userLogic.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Valida un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari a validar.
     * @return L'usuari validat.
     */
    @PutMapping("/validate/{userId}")
    public ResponseEntity<User> validateUser(@PathVariable String userId) {
        log.info("Validating user: " + userId);
        User validatedUser = userLogic.validateUser(userId);
        return ResponseEntity.ok(validatedUser);
    }

    /**
     * Restableix la contrasenya d'un usuari.
     *
     * @param request La sol·licitud de restabliment de contrasenya.
     * @return Un mapa amb un missatge d'èxit o d'error.
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean isValidUser = userLogic.userExists(request.getUsername(), request.getEmail());
        Map<String, String> response = new HashMap<>();

        if (isValidUser) {
            userLogic.updatePassword(request.getUsername(), request.getEmail());
            response.put("message",
                    "A default password has been created. IMPORTANT: Change it as soon as you login. Password: 1234");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "User not found. Please check the username and email.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}