package cat.copernic.grup4.gamedex.Core.Model

import cat.copernic.grup4.gamedex.Core.Model.StateType
import cat.copernic.grup4.gamedex.Core.Model.Videogame

data class Library(

    val idLibrary: String,
    val user: User,
    val videogame: Videogame,
    val state: StateType,
    val description: String,
    val rating: Double,
    val publishedDate: String
)
