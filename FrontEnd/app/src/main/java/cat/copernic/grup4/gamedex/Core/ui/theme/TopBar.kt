package cat.copernic.grup4.gamedex.Core.ui.theme

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.Screens.SignUpScreen
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory

@Composable
fun TopBar(navController: NavController, profileImageRes: Int, userViewModel: UserViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.header))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = profileImageRes),
            contentDescription = stringResource(R.string.profile_avatar),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White, shape = CircleShape)
                .padding(2.dp)
                .clickable {//TODO añadir redireccion al perfil
                    }
        )

        Text(
            style = GameDexTypography.headlineMedium.copy(fontSize = 48.sp),
            text = "GDEX",
            color = Color.White,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        Icon(
            Icons.Default.ExitToApp,
            contentDescription = stringResource(R.string.logout),
            Modifier.size(40.dp)
                .clickable {
                    userViewModel.logoutUser()
                    navController.navigate("login")
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    TopBar(navController = fakeNavController, profileImageRes = R.drawable.user, userViewModel = userViewModel)
}