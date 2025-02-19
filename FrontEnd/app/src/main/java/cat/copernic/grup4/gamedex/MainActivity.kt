package cat.copernic.grup4.gamedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.UI.Screens.AddCategoryScreen
import cat.copernic.grup4.gamedex.Core.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val fakeNavController = rememberNavController()
            AddCategoryScreen(navController = fakeNavController)
        }
    }
}
