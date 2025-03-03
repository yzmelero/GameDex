package cat.copernic.grup4.gamedex.Core.Model

/**
 * Classe que representa una sol·licitud d'inici de sessió.
 *
 * @param username El nom d'usuari per a l'inici de sessió.
 * @param password La contrasenya per a l'inici de sessió.
 */
data class LoginRequest(
    val username: String,
    val password: String
)