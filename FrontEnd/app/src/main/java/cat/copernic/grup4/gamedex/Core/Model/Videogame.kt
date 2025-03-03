package cat.copernic.grup4.gamedex.Core.Model

import androidx.annotation.DrawableRes
import java.time.LocalDate

/**
 * Classe que representa un videojoc.
 *
 * @param gameId L'identificador del videojoc (opcional).
 * @param nameGame El nom del videojoc.
 * @param descriptionGame La descripció del videojoc.
 * @param releaseYear L'any de llançament del videojoc.
 * @param gamePhoto La foto del videojoc en format Base64 (opcional).
 * @param ageRecommendation La recomanació d'edat per al videojoc.
 * @param developer El desenvolupador del videojoc.
 * @param category La categoria del videojoc.
 */
data class Videogame (
    val gameId: String? = null,
    val nameGame: String,
    val descriptionGame: String,
    val releaseYear: String,
    val gamePhoto: String? = null,
    val ageRecommendation: String,
    val developer: String,
    val category: String
)