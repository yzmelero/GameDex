package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel

@Composable
fun ViewValidateUserScreen(navController: NavController, userViewModel: UserViewModel) {
    val username = remember {
        navController.currentBackStackEntry?.arguments?.getString("username")
    } ?: return // Si no hay ID, salir de la función
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(username) {
        user = userViewModel.getUser(username)
    }
    val currentUser = user
    val loggedUser by userViewModel.currentUser.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.background),
            )
            .windowInsetsPadding(WindowInsets.systemBars)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header(navController, userViewModel)
        Box {
            // Profile Image
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
        user?.let { userData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.username) + ": ${userData.username}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Email: ${userData.email}", fontSize = 18.sp)
                Text(text = stringResource(R.string.name) + ": ${userData.name}", fontSize = 18.sp)
                Text(
                    text = stringResource(R.string.surname) + ": ${userData.surname}",
                    fontSize = 18.sp
                )
                Text(
                    text = stringResource(R.string.telephone) + ": ${userData.telephone}",
                    fontSize = 18.sp
                )
                Text(
                    text = stringResource(R.string.birthdate) + ": ${userData.birthDate}",
                    fontSize = 18.sp
                )
                Text(
                    text = stringResource(R.string.user_type, userData.userType.name),
                    fontSize = 18.sp
                )

                Text(
                    text = stringResource(
                        R.string.state,
                        if (userData.state) stringResource(R.string.validated) else stringResource(R.string.pending)
                    ), fontSize = 18.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            userViewModel.validateUser(userData.username)
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text(stringResource(R.string.validate_user), color = Color.White)
                    }
                    if (loggedUser?.userType == UserType.ADMIN
                        && loggedUser?.username != currentUser?.username
                    ) {
                        var showDialog by remember { mutableStateOf(false) }

                        Button(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .background(Color.Red, shape = CircleShape),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)

                        ) {
                            Text(stringResource(R.string.delete_user), color = Color.White)
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
                }
            }
        }
        BottomSection(navController, userViewModel, 2)
    }
}
