package cat.copernic.grup4.gamedexandroid.Core.Model

import java.time.LocalDate

data class Videogame (

    val gameId: String,
    val nameGame: String,
    val descriptionGame: String,
    val releaseYear: LocalDate,
    val gamePhoto: String,
    val ageRecomendation: Int,
    val developer: String,
    val nameCategory: String

)
