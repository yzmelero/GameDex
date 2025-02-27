package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import coil.compose.rememberImagePainter

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    val username = remember {
        navController.currentBackStackEntry?.arguments?.getString("username")
    } ?: return // Si no hay ID, salir de la función
    var user by remember { mutableStateOf<User?>(null) }
    val loggedUser by userViewModel.currentUser.collectAsState()



    LaunchedEffect(username) { // ✅ Llama a la función suspend en una corrutina
        user = userViewModel.getUser(username)
    }
    val currentUser = user
    val loggedUser by userViewModel.currentUser.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFDBB2FF), Color(0xFFF7E6FF))
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header
        header(navController, userViewModel)

        Spacer(modifier = Modifier.height(20.dp))
        Box {

            if (loggedUser?.userType == UserType.ADMIN
                && loggedUser?.username != currentUser?.username) {
                var showDialog by remember { mutableStateOf(false) }

                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_user),
                        tint = Color.White
                    )
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(stringResource(R.string.confirm_delete)) },
                        text = { Text(stringResource(R.string.delete_question)) },
                        confirmButton = {
                            TextButton(onClick = {
                                userViewModel.deleteUser(currentUser?.username ?: "")
                                showDialog = false
                                navController.popBackStack()
                            }) {
                                Text(stringResource(R.string.delete), color = Color.Red)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                            }) { Text(stringResource(R.string.cancel)) }
                        }
                    )
                }
            }
            Box(modifier = Modifier.size(150.dp)) { // 📌 Contenedor para superponer la imagen y el ícono
                currentUser?.let {
                    val imageBitmap = currentUser.profilePicture?.let {
                        userViewModel.base64ToBitmap(it)
                    }

                    Column {
                        imageBitmap?.let {
                            Image(
                                it, contentDescription = stringResource(R.string.profile_picture),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(320.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
        }


    }
    Spacer(modifier = Modifier.height(10.dp))

// Username
    Text(
        username, fontSize = 56.sp,
        style = GameDexTypography.bodyLarge,
        color = Color.Black
    )

    Spacer(modifier = Modifier.height(20.dp))

// Stats Section
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        //TODO: hacer un count con los datos de cada categoria para los numeros
            StatItem(stringResource(R.string.finished), "85")
        StatItem(stringResource(R.string.playing), "8")
            StatItem(stringResource(R.string.wanttoplay), "20")
    }

    Spacer(modifier = Modifier.height(20.dp))

// Library Button
    Button(
        onClick = { /* TODO: Hacer el navigation a la library */ },
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            stringResource(R.string.library),
            color = Color.White, fontSize = 18.sp,
            style = GameDexTypography.bodyLarge
        )
    }


    BottomSection(navController, userViewModel,  3)
    }
}


@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label, fontSize = 32.sp,
            style = GameDexTypography.bodyLarge,
            color = Color.Black
        )
        Text(
            value, fontSize = 28.sp,
            style = GameDexTypography.bodyLarge,
            color = Color.Gray
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    ProfileScreen(navController = fakeNavController, userViewModel)
}

