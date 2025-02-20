package cat.copernic.grup4.gamedex.Users.UI.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Core.Model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val useCases: UseCases) : ViewModel() {

    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> = _registrationSuccess

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun registerUser(user: User) {
        viewModelScope.launch {
            val response = useCases.registerUser(user)
           // _registrationSuccess.value = response.isSuccessful
            if (response.isSuccessful) {
                val user = response.body()
                _currentUser.value = user  // Desa l'usuari logejat
                _loginSuccess.value = true
            } else {
                _currentUser.value = null
                _loginSuccess.value = false
            }
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val response = useCases.loginUser(username, password)
            _loginSuccess.value = response.isSuccessful
        }
    }
}