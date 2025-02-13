package cat.copernic.grup4.gamedex.Core.Model;

import java.time.LocalDate;

public class User {
    private String username;

    private String password;

    private String name;

    private String surname;

    private String email;

    private int telephone;

    private LocalDate birthDate;

    private String profilePicture;

    // 0 for disabled or non validated users, 1 for validated users
    private Boolean state;

    private UserType userType;
}
