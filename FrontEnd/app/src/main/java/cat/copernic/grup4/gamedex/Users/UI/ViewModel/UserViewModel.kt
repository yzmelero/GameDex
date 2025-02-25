package cat.copernic.grup4.gamedex.Users.UI.ViewModel


import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Core.Model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class UserViewModel(private val useCases: UseCases) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> = _registrationSuccess
    private val _inactiveUsers = MutableStateFlow<List<User>>(emptyList())
    val inactiveUsers: StateFlow<List<User>> = _inactiveUsers

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    fun registerUser(user: User) {
        viewModelScope.launch {
            val response = useCases.registerUser(user)
            _registrationSuccess.value = response.isSuccessful
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val response = useCases.loginUser(username, password)
            _loginSuccess.value = response.isSuccessful
            if (response.isSuccessful) {

                val userResponse = useCases.getUser(username)
                if (userResponse.isSuccessful) {
                    val user = userResponse.body()
                    _currentUser.value = user
                    _loginSuccess.value = true
                } else {
                    _currentUser.value = null
                    _loginSuccess.value = false
                }
            }
        }
    }

    fun logoutUser() {
        _currentUser.value = null
        _loginSuccess.value = false
    }

    init {
        listUsers()
    }

    fun listInactiveUsers() {
        viewModelScope.launch {
            try {
                val response = useCases.listInactiveUsers()
                if (response.isSuccessful) {
                    response.body()?.let { userList ->
                        _inactiveUsers.value = userList.toList()
                    }
                } else {
                    println("Error en la API: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al obtener usuarios inactivos: ${e.message}")
            }
        }
    }

    suspend fun getUser(username: String): User? {
        return try {
            val response = useCases.getUser(username)
            if (response.isSuccessful) {
                response.body() // ✅ Extraemos el User del Response
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
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

    fun base64ToBitmap(base64: String): ImageBitmap? {
        return try {
            val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
            val byteArrayInputStream = ByteArrayInputStream(decodedBytes)
            val bitmap = BitmapFactory.decodeStream(byteArrayInputStream)
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()

            inputStream?.use { stream ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (stream.read(buffer).also { bytesRead = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead)
                }
            }

            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT) // Convertir a Base64
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun validateUser(userId: String) {
        viewModelScope.launch {
            val response = useCases.validateUser(userId)
            if (response.isSuccessful) {
                listInactiveUsers()
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val response = useCases.updateUser(user)
            if (response.isSuccessful) {
                listUsers()
                getUser(user.username)
            }
        }
    }
}