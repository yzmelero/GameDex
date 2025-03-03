package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.LoginRequest
import cat.copernic.grup4.gamedex.Core.Model.LoginResponse
import cat.copernic.grup4.gamedex.Core.Model.ResetPasswordRequest
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

/**
 * Interfície per a les operacions de la API REST d'usuaris.
 */
interface UserApiRest {

    /**
     * Registra un nou usuari.
     *
     * @param user L'usuari a registrar.
     * @return La resposta de la API amb l'usuari registrat.
     */
    @POST("user/create")
    suspend fun registerUser(@Body user: User): Response<User>

    /**
     * Obté tots els usuaris.
     *
     * @return La resposta de la API amb la llista d'usuaris.
     */
    @GET("user/all")
    suspend fun getAllUsers(): Response<List<User>>

    /**
     * Obté tots els usuaris inactius.
     *
     * @return La resposta de la API amb la llista d'usuaris inactius.
     */
    @GET("user/all/inactive")
    suspend fun getAllInactiveUsers(): Response<List<User>>

    /**
     * Verifica les credencials d'inici de sessió.
     *
     * @param credentials Les credencials d'inici de sessió.
     * @return La resposta de la API amb la informació de l'usuari.
     */
    @POST("login/verify")
    suspend fun loginUser(@Body credentials: LoginRequest): Response<LoginResponse>

    /**
     * Obté un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API amb l'usuari obtingut.
     */
    @GET("user/view/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Response<User>

    /**
     * Valida un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API amb l'usuari validat.
     */
    @PUT("user/validate/{userId}")
    suspend fun validateUser(@Path("userId") userId: String): Response<User>

    /**
     * Actualitza un usuari.
     *
     * @param user L'usuari a actualitzar.
     * @return La resposta de la API amb l'usuari actualitzat.
     */
    @PUT("user/update/{userId}")
    suspend fun updateUser(@Body user: User): Response<User>

    /**
     * Elimina un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API.
     */
    @DELETE("user/delete/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): Response<Void>

    /**
     * Restableix la contrasenya d'un usuari.
     *
     * @param request La sol·licitud de restabliment de contrasenya.
     * @return La resposta de la API amb el resultat del restabliment.
     */
    @POST("user/resetPassword")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<Map<String, String>>

    /**
     * Obté tots els usuaris associats a un ID d'usuari.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API amb la llista d'usuaris.
     */
    @GET("user/all/{userId}")
    suspend fun getAllUsersByUserId(@Path("userId") userId: String): Response<List<User>>

    /**
     * Compta el nombre de jocs en una biblioteca per estat.
     *
     * @param userId L'ID de l'usuari.
     * @param state  L'estat dels jocs.
     * @return La resposta de la API amb el nombre de jocs.
     */
    @GET("library/count/{userId}/{state}")
    suspend fun countByUserAndState(@Path("userId") userId: String, @Path("state") state: String): Response<Int>
}