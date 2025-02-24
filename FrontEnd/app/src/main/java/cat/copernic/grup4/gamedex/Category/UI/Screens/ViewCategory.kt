package cat.copernic.grup4.gamedex.Category.UI.Screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
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
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory


@Composable
fun ViewCategoryScreen(navController: NavController, userViewModel: UserViewModel) {
    val categoryId = remember {
        navController.currentBackStackEntry?.arguments?.getString("categoryId")
    } ?: return

    val categoryCases = CategoryCases(CategoryRepository())
    val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModelFactory(categoryCases))
    var category by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(categoryId) {
        category = categoryViewModel.getCategoryById(categoryId)
    }

    val currentCategory = category

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFDBB2FF), Color(0xFFF7E6FF))
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        header(navController)

        Spacer(modifier = Modifier.height(16.dp))

        // Título
        Text(
            text = stringResource(R.string.category),
            fontSize = 50.sp,
            color = Color.Black,
            style = GameDexTypography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar Categoría
        currentCategory?.let {
            CategoryDetails(navController, it, categoryViewModel)
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomSection(navController, userViewModel,0)
    }
}

@Composable
fun CategoryDetails(navController: NavController, category: Category, viewModel: CategoryViewModel) {
    val categoryImage = category.categoryPhoto?.let {
        viewModel.base64ToBitmap(it)?.asImageBitmap()
    }

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
                text = category.nameCategory,
                fontSize = 40.sp,
                style = GameDexTypography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            categoryImage?.let {
                Image(
                    bitmap = it,
                    contentDescription = stringResource(R.string.image_category),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = category.description, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("list_category") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.modify), color = Color.White)
            }
        }
    }

    DeleteButton(
        modifier = Modifier
            .offset(x = (-8).dp, y = (-8).dp)
    )
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
                contentDescription = stringResource(R.string.delete_category),
                Modifier.size(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewViewCategoryScreen() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    ViewCategoryScreen(navController = fakeNavController, userViewModel)
}
