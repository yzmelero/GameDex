package cat.copernic.grup4.gamedex.Category.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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

    val categoryId = navController.currentBackStackEntry?.arguments?.getString("categoryId") ?: return
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
            )
            .verticalScroll(rememberScrollState()), // Habilita el scroll vertical
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header(navController)

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Nombre de la categoría
                        currentCategory?.let {
                            Text(
                                it.nameCategory,
                                fontSize = 32.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                style = GameDexTypography.bodyLarge,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Imagen de la categoría
                        currentCategory?.let {
                            val imageBitmap = it.categoryPhoto?.let { userViewModel.base64ToBitmap(it) }
                            imageBitmap?.let { bitmap ->
                                Image(
                                    bitmap,
                                    contentDescription = stringResource(R.string.profile_picture),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Descripción
                        currentCategory?.let {
                            Text(
                                it.description,
                                fontSize = 18.sp,
                                style = GameDexTypography.bodyLarge,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Botón de Modificar
                        Button(
                            onClick = { navController.navigate("list_category") },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                stringResource(R.string.modify),
                                color = Color.White,
                                fontSize = 18.sp,
                                style = GameDexTypography.bodyLarge
                            )
                        }
                    }

                    // Botón de Eliminar en la esquina superior derecha
                    IconButton(
                        onClick = {
                            //categoryViewModel.deleteCategory(categoryId)
                            //navController.popBackStack()
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(Color.Red, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomSection(navController, userViewModel, 0)
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