package cat.copernic.grup4.gamedex.Users.UI.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cat.copernic.grup4.gamedex.Core.Model.LoginRequest
import cat.copernic.grup4.gamedex.Core.Model.LoginResponse
import cat.copernic.grup4.gamedex.Users.Data.UserRepository

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginState = MutableLiveData<Result<LoginResponse>>()
    val loginState: LiveData<Result<LoginResponse>> get() = _loginState

    fun login(username: String, password: String) {
        val request = LoginRequest(username, password)
        repository.loginUser(request) { result ->
            _loginState.postValue(result)
        }
    }
}