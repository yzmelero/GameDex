package cat.copernic.grup4.gamedex.Core.Model

import java.time.LocalDate

data class User(

    val username: String,
    val password: String,
    val name: String,
    val surname: String,
    val email: String,
    val telephone: Int,
    val birthDate: String,

    val profilePicture: String? = null


)
