package cat.copernic.grup4.gamedex.Users.UI.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.copernic.grup4.gamedex.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.LoginRequest
import cat.copernic.grup4.gamedex.Core.Model.LoginState
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.Users.Data.RetrofitInstance
import cat.copernic.grup4.gamedex.Users.Data.UserApiRest
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel) {
    val userRepository = UserRepository()
    val useCases = UseCases(userRepository) // Aquí has d'obtenir la instància real
    val factory = UserViewModelFactory(useCases)

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginSuccess by userViewModel.loginSuccess.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess == true) {
            navController.navigate("category")  // Si el login és correcte, naveguem
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.gdexlogo),
            contentDescription = "logo",
            modifier = Modifier
                .size(400.dp),
        )
        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.Black,
            style = GameDexTypography.bodyLarge,
            modifier = Modifier
                .offset(y = (-100).dp) // ⬆ Mou el text cap amunt sense afectar la imatge
        )
        Box(
            modifier = Modifier
                .offset(y = (-50).dp)
                .clip(RoundedCornerShape(30.dp))
                .background(colorResource(id = R.color.boxbackground))
                .fillMaxWidth(0.8f)
                .wrapContentHeight()
                .defaultMinSize(minHeight = 200.dp)
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = Color.Black,
                style = GameDexTypography.headlineMedium,
                fontSize = 36.sp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(vertical = 16.dp)
            )

            Column(
                modifier = Modifier
                    .padding(16.dp, top = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputField(
                    label = stringResource(id = R.string.username),
                    value = username
                ) { username = it }
                InputField(
                    label = stringResource(id = R.string.password),
                    value = password
                ) { password = it }
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    onClick = { userViewModel.loginUser(username, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bubblegum)),
                )
                {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        color = Color.White,
                        style = GameDexTypography.headlineMedium
                    )
                }
                if (loginSuccess == false) {
                    Text(
                        text = "Error en el login",
                        color = Color.Red,
                        style = GameDexTypography.bodyMedium
                    )
                }
            }
            TextButton(onClick = {
                // Acción de olvidar la contraseña
            }) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = Color.Black,
                    style = GameDexTypography.labelSmall,
                    fontSize = 18.sp,
                )
            }
            Button(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                onClick = { navController.navigate("signup") },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bubblegum)),
            )
            {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    color = Color.White,
                    style = GameDexTypography.headlineMedium
                )
            }


        }
    }
}

//NO FUNCIONA!
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = GameDexTypography.bodyMedium.copy(fontSize = 18.sp)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = GameDexTypography.bodyMedium.copy(fontSize = 18.sp),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray
        )
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    LoginScreen(
        navController = fakeNavController,
        userViewModel = UserViewModel(UseCases(UserRepository()))
    )  // ✅ Passar-lo a LoginScreen
}