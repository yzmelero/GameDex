package cat.copernic.grup4.gamedexandroid.Core.Model

import java.time.LocalDate

data class User(
    val username: String,
    val password: String,
    val name: String,
    val surname: String,
    val email: String,
    val telephone: Int,

    val birthDate: LocalDate,

    val profilePicture: ByteArray, // MongoDB almacena Binary[], en Kotlin usamos ByteArray

    val state: Boolean, // 0 (false) para deshabilitado, 1 (true) para validado

    val userType: UserType
)
