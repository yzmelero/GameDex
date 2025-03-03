package cat.copernic.grup4.gamedex.Core.Model

/**
 * Classe que representa una resposta d'inici de sessió.
 *
 * @param username El nom d'usuari de la resposta d'inici de sessió.
 * @param userType El tipus d'usuari de la resposta d'inici de sessió.
 */
data class LoginResponse(
    val username: String,
    val userType: UserType
)