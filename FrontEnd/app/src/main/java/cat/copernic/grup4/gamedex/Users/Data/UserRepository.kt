package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.LoginRequest
import cat.copernic.grup4.gamedex.Core.Model.LoginResponse
import cat.copernic.grup4.gamedex.Core.Model.ResetPasswordRequest
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Users.Data.RetrofitInstance.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repositori per gestionar les operacions de la API REST d'usuaris.
 */
class UserRepository {

    /**
     * Registra un nou usuari.
     *
     * @param user L'usuari a registrar.
     * @return La resposta de la API amb l'usuari registrat.
     */
    suspend fun registerUser(user: User): Response<User> {
        return RetrofitInstance.api.registerUser(user)
    }

    /**
     * Verifica les credencials d'inici de sessió.
     *
     * @param username El nom d'usuari.
     * @param password La contrasenya.
     * @return La resposta de la API amb la informació de l'usuari.
     */
    suspend fun loginUser(username: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(username, password)
        return RetrofitInstance.api.loginUser(request)
    }

    /**
     * Obté tots els usuaris.
     *
     * @return La resposta de la API amb la llista d'usuaris.
     */
    suspend fun getAllUsers(): Response<List<User>> {
        return RetrofitInstance.api.getAllUsers()
    }

    /**
     * Obté tots els usuaris inactius.
     *
     * @return La resposta de la API amb la llista d'usuaris inactius.
     */
    suspend fun getAllInactiveUsers(): Response<List<User>> {
        return RetrofitInstance.api.getAllInactiveUsers()
    }

    /**
     * Obté un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return La resposta de la API amb l'usuari obtingut.
     */
    suspend fun getUser(username: String): Response<User> {
        return RetrofitInstance.api.getUser(username)
    }

    /**
     * Valida un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API amb l'usuari validat.
     */
    suspend fun validateUser(userId: String): Response<User> {
        return RetrofitInstance.api.validateUser(userId)
    }

    /**
     * Actualitza un usuari.
     *
     * @param user L'usuari a actualitzar.
     * @return La resposta de la API amb l'usuari actualitzat.
     */
    suspend fun updateUser(user: User): Response<User> {
        return RetrofitInstance.api.updateUser(user)
    }

    /**
     * Elimina un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API.
     */
    suspend fun deleteUser(userId: String): Response<Void> {
        return RetrofitInstance.api.deleteUser(userId)
    }

    /**
     * Restableix la contrasenya d'un usuari.
     *
     * @param username El nom d'usuari.
     * @param email El correu electrònic associat a la sol·licitud de restabliment de contrasenya.
     * @return La resposta de la API amb el resultat del restabliment.
     */
    suspend fun resetPassword(username: String, email: String): Response<Map<String, String>> {
        val request = ResetPasswordRequest(username, email)
        return RetrofitInstance.api.resetPassword(request)
    }

    /**
     * Obté tots els usuaris associats a un ID d'usuari.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API amb la llista d'usuaris.
     */
    suspend fun getAllUsersByUserId(userId: String): Response<List<User>> {
        return RetrofitInstance.api.getAllUsersByUserId(userId)
    }

    /**
     * Compta el nombre de jocs en una biblioteca per estat.
     *
     * @param userId L'ID de l'usuari.
     * @param state L'estat dels jocs.
     * @return La resposta de la API amb el nombre de jocs.
     */
    suspend fun countByUserAndState(userId: String, state: String): Response<Int> {
        return RetrofitInstance.api.countByUserAndState(userId, state)
    }
}