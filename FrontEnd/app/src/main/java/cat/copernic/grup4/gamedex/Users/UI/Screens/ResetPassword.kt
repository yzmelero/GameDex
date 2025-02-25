package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory

@Composable
fun ResetPasswordScreen(navController: NavController, userViewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val resetMessage by userViewModel.resetMessage.collectAsState()

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
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 12.dp),
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Para que ocupe el espacio restante
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = stringResource(id = R.string.reset_password),
                fontSize = 40.sp,
                style = GameDexTypography.bodyLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            )
            {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField(
                        label = stringResource(id = R.string.username),
                        value = username
                    ) { username = it }
                    InputField(
                        label = stringResource(id = R.string.email),
                        value = email
                    )
                    { email = it }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        onClick = { userViewModel.resetPassword(username, email) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),

                        ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            color = Color.White,
                            style = GameDexTypography.bodySmall.copy(fontSize = 16.sp),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            if(!resetMessage.isNullOrEmpty()){
                Text(
                    text = resetMessage!!,
                    color = Color.Black,
                    style = GameDexTypography.bodySmall.copy(fontSize = 18.sp),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4))
                ) {
                    Text(
                        text = stringResource(id = R.string.accept),
                        color = Color.White,
                        style = GameDexTypography.bodySmall.copy(fontSize = 16.sp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResetPasswordScreen() {
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    ResetPasswordScreen(navController = fakeNavController, userViewModel)
}

