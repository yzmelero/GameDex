package cat.copernic.gamedex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import cat.copernic.gamedex.entity.User;
import cat.copernic.gamedex.entity.UserType;
import cat.copernic.gamedex.logic.UserLogic;
import cat.copernic.gamedex.repository.UserRepository;

public class UserLogicTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserLogic userLogic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userLogic.createUser(user);
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    public void testCreateUser_Success() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findById(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userLogic.createUser(user);

        assertNotNull(createdUser);
        assertFalse(createdUser.getState());
        assertEquals(UserType.USER, createdUser.getUserType());
    }

    @Test
    public void testCreateAdmin_UserAlreadyExists() {
        User user = new User();
        user.setUsername("testAdmin");

        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userLogic.createAdmin(user);
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    public void testCreateAdmin_Success() {
        User user = new User();
        user.setUsername("testAdmin");

        when(userRepository.findById(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User createdAdmin = userLogic.createAdmin(user);

        assertNotNull(createdAdmin);
        assertTrue(createdAdmin.getState());
        assertEquals(UserType.ADMIN, createdAdmin.getUserType());
    }

    @Test
    public void testModifyUser_UserNotFound() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findById(user.getUsername())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userLogic.modifyUser(user);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testModifyUser_Success() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setUsername("testUser");
        existingUser.setPassword("oldPassword");

        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User modifiedUser = userLogic.modifyUser(user);

        assertNotNull(modifiedUser);
        assertEquals("newPassword", modifiedUser.getPassword());
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        String username = "testUser";

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userLogic.deleteUser(username);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testDeleteUser_Success() {
        String username = "testUser";

        User user = new User();
        user.setUsername(username);

        when(userRepository.findById(username)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> {
            userLogic.deleteUser(username);
        });

        verify(userRepository, times(1)).deleteById(username);
    }

    @Test
    public void testGetAllUsers_Success() {
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userLogic.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
    }
}
