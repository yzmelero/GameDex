package cat.copernic.grup4.gamedex.Core.Model

import androidx.annotation.DrawableRes
import java.time.LocalDate
package cat.copernic.grup4.gamedex.Core.Model

data class Videogame (

    val gameId: String? = null,
    val nameGame: String,
    val descriptionGame: String,
    val releaseYear: String,
    val gamePhoto: String? = null,
    val ageRecommendation: String,
    val developer: String,
    val nameCategory: String

)
