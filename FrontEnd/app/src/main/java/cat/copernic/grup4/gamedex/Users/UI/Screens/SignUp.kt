package cat.copernic.grup4.gamedex.Users.UI.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import java.time.LocalDate

@Composable
fun SignUpScreen(navController: NavController) {

    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    val context = LocalContext.current
    val registrationState by userViewModel.registrationSuccess.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(bottom = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Columna para el título "GDEX"
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.header)) // Fondo más oscuro
                .padding(12.dp), // Añadido padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, end = 56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono a la izquierda
                FloatingActionButton(
                    onClick = {  navController.popBackStack() },
                    modifier = Modifier.size(40.dp).padding(top = 12.dp),
                    containerColor = colorResource(R.color.header)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Título centrado en la pantalla
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "GDEX",
                        style = GameDexTypography.headlineMedium.copy(fontSize = 48.sp),
                        color = Color.White
                    )
                }
            }

        }

        // Columna para el resto del contenido
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Para que ocupe el espacio restante
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.sign_up),
                fontSize = 40.sp,
                style = GameDexTypography.bodyLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),


                ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField(
                        label = stringResource(id = R.string.username),
                        value = username

                    ) { username = it }
                    InputField(
                        label = stringResource(id = R.string.password),
                        value = password,
                        isPassword = true
                    ) { password = it }
                    InputField(label = stringResource(id = R.string.name), value = name) {
                        name = it
                    }
                    InputField(
                        label = stringResource(id = R.string.surname),
                        value = surname
                    ) { surname = it }
                    InputField(label = stringResource(id = R.string.email), value = email) {
                        email = it
                    }
                    InputField(
                        label = stringResource(id = R.string.telephone),
                        value = telephone,
                        keyboardType = KeyboardType.Number
                    ) { telephone = it }
                    InputField(
                        label = stringResource(id = R.string.birthdate),
                        value = birthDate
                    ) { birthDate = it }

                    Text(
                        text = "Avatar",
                        style = GameDexTypography.bodySmall.copy(fontSize = 16.sp),
                        color = Color.Black
                        )

                    AvatarSection()

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        //TODO añadir acción de registro
                        onClick = { val newUser = User(
                            username = username,
                            password = password,
                            name = name,
                            surname = surname,
                            email = email,
                            telephone = telephone.toIntOrNull() ?: 0, // Convertir telèfon a Int
                            birthDate = birthDate,
                            profilePicture = null
                        )
                            userViewModel.registerUser(newUser) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),

                        ) {
                        Text(text = stringResource(id = R.string.confirm),
                            color = Color.White,
                            style = GameDexTypography.bodySmall.copy(fontSize = 16.sp),
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(registrationState) {
        registrationState?.let { success ->
            if (success) {
                Toast.makeText(context, context.getString(R.string.user_created), Toast.LENGTH_LONG).show()
                navController.navigate("login")
            } else {
                Toast.makeText(context,
                    context.getString(R.string.error_creating_user), Toast.LENGTH_LONG).show()
            }
        }
    }

}

@Composable
fun InputField(
    label: String,
    value: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, color = Color.Black, fontSize = 14.sp)
        TextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
fun AvatarSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.coche),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(50))
                    //TODO añadir acción para elegir imagen
                    .clickable { /* Acción para elegir imagen */ }
            )
            Icon(
                Icons.Default.Add,
                contentDescription = stringResource(R.string.add_avatar),
                modifier = Modifier
                    .clickable { /*TODO Acción para elegir imagen */ }
                    .padding(top = 40.dp)
                    .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .size(40.dp)
            )
        }
    }
}

fun addImage(){

}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    SignUpScreen(navController = fakeNavController)
}