package cat.copernic.grup4.gamedex.Users.UI.ViewModel


import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Core.Model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream

class UserViewModel(private val useCases: UseCases) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> = _registrationSuccess

    fun registerUser(user: User) {
        viewModelScope.launch {
            val response = useCases.registerUser(user)
            _registrationSuccess.value = response.isSuccessful
        }
    }

    init {
        listUsers()
    }

    fun listUsers() {
        viewModelScope.launch {
            try {
                val response =
                    useCases.listUsers()
                if (response.isSuccessful) {
                    response.body()?.let { userList ->
                        _users.value = userList
                    }
                } else {
                    println("Error en la API: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al obtener usuarios: ${e.message}")
            }
        }

    }

    fun uriToByteArray(uri: Uri, contentResolver: ContentResolver): ByteArray {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()

        inputStream?.use { input ->
            val buffer = ByteArray(1024)
            var length: Int
            while (input.read(buffer).also { length = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, length)
            }
        }

        return byteArrayOutputStream.toByteArray()
    }
}