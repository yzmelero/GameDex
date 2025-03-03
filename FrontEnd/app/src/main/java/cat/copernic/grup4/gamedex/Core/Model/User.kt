package cat.copernic.grup4.gamedex.Core.Model

/**
 * Classe que representa un usuari.
 *
 * @param username El nom d'usuari.
 * @param password La contrasenya de l'usuari.
 * @param name El nom de l'usuari.
 * @param surname El cognom de l'usuari.
 * @param email El correu electrònic de l'usuari.
 * @param telephone El telèfon de l'usuari.
 * @param birthDate La data de naixement de l'usuari.
 * @param userType El tipus d'usuari.
 * @param state L'estat de l'usuari (true per a actiu, false per a inactiu).
 * @param profilePicture La foto de perfil de l'usuari en format Base64 (opcional).
 */
data class User(
    val username: String,
    val password: String,
    val name: String,
    val surname: String,
    val email: String,
    val telephone: Int,
    val birthDate: String,
    val userType: UserType,
    val state: Boolean,
    val profilePicture: String? = null
)