package cat.copernic.grup4.gamedex.Library.Data

import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LibraryApiRest {
    @POST("library/add")
    suspend fun addGameToLibrary(@Body library: Library): Response<Library>

}