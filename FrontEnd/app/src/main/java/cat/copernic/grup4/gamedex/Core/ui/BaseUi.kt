package cat.copernic.grup4.gamedex.Core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel

@Composable
fun header(navController: NavController, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(navController ,profileImageRes = R.drawable.coche, userViewModel)

    }
}
@Composable
fun BottomSection(navController: NavController, userViewModel: UserViewModel, posicion: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Bottom
    ) {
        BottomNavBar(navController, userViewModel, selectedItem = posicion)
    }
}