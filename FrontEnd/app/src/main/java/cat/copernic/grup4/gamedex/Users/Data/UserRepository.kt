package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.LoginRequest
import cat.copernic.grup4.gamedex.Core.Model.LoginResponse
import cat.copernic.grup4.gamedex.Core.Model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    suspend fun registerUser(user: User): Response<User> {
        return RetrofitInstance.api.registerUser(user)
    }

    fun loginUser(loginRequest: LoginRequest, callback: (Result<LoginResponse>) -> Unit) {
        RetrofitInstance.api.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback(Result.success(it)) }
                } else {
                    callback(Result.failure(Exception("Error: ${response.message()}")))
                }
                }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}