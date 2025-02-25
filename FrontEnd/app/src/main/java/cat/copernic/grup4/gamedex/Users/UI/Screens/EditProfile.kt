package cat.copernic.grup4.gamedex.Users.UI.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.R
import coil.compose.AsyncImage
import java.time.LocalDate

@Composable
fun EditProfileScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val currentUser by userViewModel.currentUser.collectAsState()
    var modifyUser by remember { mutableStateOf<User?>(null) }
    val entryUser = remember {
        navController.currentBackStackEntry?.arguments?.getString("username")
    } ?: return // Si no hay ID, salir de la función
    LaunchedEffect(entryUser) { // ✅ Llama a la función suspend en una corrutina
        modifyUser = userViewModel.getUser(entryUser)
    }
    val user = modifyUser
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf(0) }
    var birthDate by remember { mutableStateOf("") }
    var profilePicture by remember { mutableStateOf<Uri?>(null) }
    var selectedImage by remember { mutableStateOf("") }



    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> profilePicture = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header(navController, userViewModel)

        Text(
            text = stringResource(id = R.string.edit_profile),
            fontSize = 28.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clickable {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (currentUser?.profilePicture != null) {
                currentUser?.let {
                    val imageBitmap = currentUser!!.profilePicture?.let {
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
            } else if (profilePicture != null) {
                selectedImage =
                    userViewModel.uriToBase64(context, profilePicture!!).toString()
                AsyncImage(
                    model = selectedImage,
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
            else {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = stringResource(id = R.string.profile_picture),
                    modifier = Modifier
                        .size(120.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        user?.username?.let {
            InputFieldEdit(
                label = stringResource(id = R.string.username),
                value = it
            ) { username = it }
        }
        user?.password?.let {
            InputFieldEdit(
                label = stringResource(id = R.string.password),
                value = it,
                isPassword = true
            ) { password = it }
        }
        user?.name?.let {
            InputFieldEdit(
                label = stringResource(id = R.string.name),
                value = it
            ) { name = it }
        }
        user?.surname?.let {
            InputFieldEdit(
                label = stringResource(id = R.string.surname),
                value = it
            ) { surname = it }
        }
        user?.email?.let {
            InputFieldEdit(
                label = stringResource(id = R.string.email),
                value = it
            ) { email = it }
        }
        InputFieldEdit(
            label = stringResource(id = R.string.telephone),
            value = user?.telephone.toString(),
            keyboardType = KeyboardType.Number
        ) { telephone = it.toInt() }
        user?.birthDate?.let {
            InputFieldEdit(
                label = stringResource(id = R.string.birthdate),
                value = it
            ) { birthDate = it }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updatedUser = User(
                    username = currentUser!!.username,
                    password = password!!,
                    name = name!!,
                    surname = surname!!,
                    email = email!!,
                    telephone = telephone.toInt(),
                    birthDate = birthDate!!,
                    profilePicture = profilePicture?.let { userViewModel.uriToBase64(context, it) },
                    state = currentUser?.state ?: false,
                    userType = currentUser?.userType ?: UserType.USER
                )
                userViewModel.updateUser(updatedUser) { success ->
                    if (success) {
                        Toast.makeText(context, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = stringResource(id = R.string.save_changes), color = Color.White)
        }
        BottomSection(navController, userViewModel, 3)
    }
}

@Composable
fun InputFieldEdit(
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
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}