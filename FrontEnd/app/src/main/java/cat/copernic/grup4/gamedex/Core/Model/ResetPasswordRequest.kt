package cat.copernic.grup4.gamedex.Core.Model

/**
 * Classe que representa una sol·licitud de restabliment de contrasenya.
 *
 * @param username El nom d'usuari per a la sol·licitud de restabliment de contrasenya.
 * @param email El correu electrònic associat a la sol·licitud de restabliment de contrasenya.
 */
data class ResetPasswordRequest(
    val username: String,
    val email: String
)