package cat.copernic.grup4.gamedex.Core.Model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class Videogame (

    val gameId: String,
    val nameGame: String,
    val descriptionGame: String,
    val releaseYear: Int,
    @DrawableRes val gamePhoto: Int,
    val ageRecommendation: Int,
    val developer: String,
    val nameCategory: String

)
