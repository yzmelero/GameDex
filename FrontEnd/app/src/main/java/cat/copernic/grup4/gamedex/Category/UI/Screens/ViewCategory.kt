package cat.copernic.grup4.gamedex.Category.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R

@Composable
fun ViewCategoryScreen(navController: NavController, categoryName: String, categoryDescription: String, imageRes: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3E5F5)) // Fons similar al gradient de la imatge
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(onLogoutClick = {}, profileImageRes = R.drawable.user)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.category),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = categoryName, fontSize = 22.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    IconButton(onClick = { /* Acció per eliminar */ }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Category")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Category Image",
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = categoryDescription, fontSize = 14.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Acció per modificar */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.modify), color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavBar(onItemSelected = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewViewCategoryScreen() {
    val fakeNavController = rememberNavController()
    ViewCategoryScreen(navController = fakeNavController, categoryName = "Fantasy", categoryDescription = "A genre set in magical worlds...", imageRes = R.drawable.coche)
}
