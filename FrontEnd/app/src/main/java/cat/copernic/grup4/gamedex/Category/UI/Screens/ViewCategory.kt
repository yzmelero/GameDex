package cat.copernic.grup4.gamedex.Category.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModel
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModelFactory
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R

@Composable
fun ViewCategoryScreen(navController: NavController) {
    val nameCategory = "Hola"

    val categoryCases = CategoryCases(CategoryRepository())
    val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModelFactory(categoryCases))

    LaunchedEffect(nameCategory) {
        categoryViewModel.getCategoryById(nameCategory)
    }

    val categoryGetById by categoryViewModel.categoryGetById.collectAsState()

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

            CategoryHeader()

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                CategoryCard(navController)
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

@Composable
fun CategoryHeader() {
    Text(
        text = stringResource(R.string.category),
        fontSize = 50.sp,
        color = Color.Black,
        style = GameDexTypography.bodyLarge
    )
}

@Composable
fun CategoryCard(navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = categoryName,
                    fontSize = 40.sp,
                    style = GameDexTypography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Category Image",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = categoryDescription, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("modify_category") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.modify), color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        DeleteButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-8).dp, y = (-8).dp)
        )
    }
}


@Composable
fun DeleteButton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(colorResource(R.color.header), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = { /* Acció per eliminar */ }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Category",
                Modifier.size(30.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewViewCategoryScreen() {
    val fakeNavController = rememberNavController()
    ViewCategoryScreen(navController = fakeNavController)
}
