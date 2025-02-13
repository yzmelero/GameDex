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

    public User() {
    }

    public User(String username, String password, String name, String surname,
                String email, int telephone, LocalDate birthDate, String profilePicture,
                Boolean state, UserType userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;
        this.state = state;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
