package cat.copernic.grup4.gamedex.Category.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModel
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModelFactory
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory

@Composable
fun ViewCategoryScreen(navController: NavController, userViewModel: UserViewModel) {
    val categoryCases = CategoryCases(CategoryRepository())
    val categoryViewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelFactory(categoryCases))
    //val categoryViewModel: CategoryViewModel = viewModel()
    val categoryId = remember {
        navController.currentBackStackEntry?.arguments?.getString("categoryId")
    } ?: return
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

        Spacer(modifier = Modifier.height(20.dp))

        // Category Name
        currentCategory?.let {
            Text(
                categoryId//it.nameCategory
                , fontSize = 56.sp,
                style = GameDexTypography.bodyLarge,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Category Image
        currentCategory?.let {
            val imageBitmap = currentCategory.categoryPhoto?.let {
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

        Spacer(modifier = Modifier.height(20.dp))

        // Category Description
        currentCategory?.let {
            Text(
                it.description, fontSize = 20.sp,
                style = GameDexTypography.bodyLarge,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Modify Button
        Button(
            onClick = { navController.navigate("list_category") },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                stringResource(R.string.modify),
                color = Color.White, fontSize = 18.sp,
                style = GameDexTypography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomSection(navController, userViewModel, 3)
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    ViewCategoryScreen(navController = fakeNavController, userViewModel)
}

