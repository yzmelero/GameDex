package cat.copernic.grup4.gamedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cat.copernic.grup4.gamedex.Category.UI.Screens.ListCategoryScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.PreviewSignUpScreen
import cat.copernic.grup4.gamedex.Users.UI.Screens.SignUpScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            //PreviewSignUpScreen()
            ListCategoryScreen()
        }
    }
}
