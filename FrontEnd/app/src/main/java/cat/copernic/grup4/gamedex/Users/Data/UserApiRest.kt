package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.LoginRequest
import cat.copernic.grup4.gamedex.Core.Model.LoginResponse
import cat.copernic.grup4.gamedex.Core.Model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserApiRest {

    @POST("user/create") // Endpoint del backend
    suspend fun registerUser(@Body user: User): Response<User>

    @GET("user/all")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("user/all/inactive")
    suspend fun getAllInactiveUsers(): Response<List<User>>

    @POST("login/verify")
    suspend fun loginUser(@Body credentials: LoginRequest): Response<LoginResponse>

    @GET("user/view/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<User>

     @PUT("user/validate/{userId}")
    suspend fun validateUser(@Path("userId") userId: String): Response<User>

/*
    @Headers("Content-Type: application/json")
    @POST("user/create")
    suspend fun createUser(@Body user: User): Response<User>

    @Headers("Content-Type: application/json")
    @PUT("user/update")
    suspend fun updateUser(@Body user: User): Response<User>

    @DELETE("user/delete/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): Response<Unit>*/
}
