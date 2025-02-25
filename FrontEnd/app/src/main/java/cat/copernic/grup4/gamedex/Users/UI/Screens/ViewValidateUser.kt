package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cat.copernic.grup4.gamedex.Core.Model.User
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


                Spacer(modifier = Modifier.height(16.dp))

                userData.profilePicture?.let { base64 ->
                    userViewModel.base64ToBitmap(base64)?.let { image ->
                        Image(
                            bitmap = image,
                            contentDescription = stringResource(R.string.profile_picture),
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                    }
                }

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

                    Button(
                        onClick = {
                            // TODO userViewModel.deleteUser(userData.username)
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(stringResource(R.string.delete_user), color = Color.White)
                    }
                }
            }
        } ?: Text(
            text = stringResource(R.string.loading),
            modifier = Modifier.fillMaxSize(),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        BottomSection(navController, userViewModel, 2)
    }
}
