package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.User
import retrofit2.Call
import retrofit2.Response

class UserRepository {
    fun createUser(user: User): Response<User> {
        return UserRetrofitInstance.retrofitInstance.createUser(user) // ✅ Llamada correcta a la API
    }
}