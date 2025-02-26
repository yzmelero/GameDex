package cat.copernic.grup4.gamedex.Core.Model

data class ResetPasswordRequest (
    val username: String,
    val email: String
)