package cat.copernic.grup4.gamedex.Users.Data

import cat.copernic.grup4.gamedex.Core.Model.User
import retrofit2.Response

class UserRepository {
    suspend fun registerUser(user: User): Response<User> {
        return RetrofitInstance.api.registerUser(user)
    }
}