package cat.copernic.grup4.gamedexandroid.Core.Model

import java.time.LocalDate

data class Commentary(

    val idCommentary: String,
    val state: StateType,
    val description: String,
    val rating: Double,
    val publishedDate: LocalDate,
    val user: User,
    //val videogame: Videogame

)
enum class StateType{
    PLAYING,WANTTOPLAY,FINISHED,DROPPED
}