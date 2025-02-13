package cat.copernic.grup4.gamedex.Users.UI.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val useCases: UseCases) : ViewModel() {

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _operationSuccess = MutableStateFlow<Boolean?>(null)
    val operationSuccess: StateFlow<Boolean?> = _operationSuccess

    fun createUser(user: User) {
        viewModelScope.launch {
            val response: Response<User> = useCases.createUser(user)
            _operationSuccess.value = response.isSuccessful
        }
    }
}
/*
    fun getAllUsers() {
        viewModelScope.launch {
            val response: Response<List<User>> = useCases.getAllUsers()
            if (response.isSuccessful) {
                _userList.value = response.body() ?: emptyList()
            }
        }
    }

    fun getUserById(userId: String) {
        viewModelScope.launch {
            val response: Response<User> = useCases.getUserById(userId)
            if (response.isSuccessful) {
                _user.value = response.body()
            }
        }
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            val response: Response<User> = useCases.createUser(user)
            _operationSuccess.value = response.isSuccessful
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val response: Response<User> = useCases.updateUser(user)
            _operationSuccess.value = response.isSuccessful
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            val response: Response<Unit> = useCases.deleteUser(userId)
            _operationSuccess.value = response.isSuccessful
        }
    }
}

/*import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Users.Data.UserApiRest
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository() // ✅ Instancia correcta

    fun createUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.createUser(user)
                if (response.)
            } catch (e: Exception) {
                onError("Error: ${e.message}")
            }
        }
    }

    /*fun createUser(user: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val call = userRepository.createUser(user)
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Error en la creación del usuario")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    onError("Error: ${t.message}")
                }
            })
        }
    }*/
}*/