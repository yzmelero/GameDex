package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserApiRest {

    @GET("all")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("byId/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): Response<User>

    @POST("create")
    fun createUser(@Body user: User): Call<User>

    @PUT("update")
    suspend fun updateUser(@Body user: User): Response<User>

    @DELETE("delete/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): Response<Void>
}
