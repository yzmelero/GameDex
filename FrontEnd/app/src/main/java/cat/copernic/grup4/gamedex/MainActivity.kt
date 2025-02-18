package cat.copernic.grup4.gamedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cat.copernic.grup4.gamedex.Core.ui.navigation.AppNavigation
import cat.copernic.grup4.gamedex.Users.UI.Screens.UserListScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            //AppNavigation()
            UserListScreen()
        }
    }
}
