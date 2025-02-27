package cat.copernic.grup4.gamedex.Category.UI.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.rememberAsyncImagePainter

@Composable
fun EditCategoryScreen(navController: NavController, userViewModel: UserViewModel) {
    val categoryCases = CategoryCases(CategoryRepository())
    val categoryViewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelFactory(categoryCases))

    val categoryId = navController.currentBackStackEntry?.arguments?.getString("categoryId") ?: return
    var category by remember { mutableStateOf<Category?>(null) }
    var categoryName by remember { mutableStateOf("") }
    var categoryDescription by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }

    // Get category data by ID
    LaunchedEffect(categoryId) {
        category = categoryViewModel.getCategoryById(categoryId)
        category?.let {
            categoryName = it.nameCategory
            categoryDescription = it.description
            imageUri = it.categoryPhoto
        }
    }

    val context = LocalContext.current
    val categoryModified by categoryViewModel.categoryModified.collectAsState()

    // Observe category modification result
    LaunchedEffect(categoryModified) {
        if (categoryModified == true) {
            Toast.makeText(context, R.string.category_updated, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header(navController, userViewModel)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category Name Field
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text(stringResource(R.string.name_category)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Category Description Field
                OutlinedTextField(
                    value = categoryDescription,
                    onValueChange = { categoryDescription = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Category Image Preview
                imageUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = stringResource(R.string.photo_category),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Button to Save Changes
                Button(
                    onClick = {
                        val updatedCategory = Category(
                            nameCategory = categoryName,
                            description = categoryDescription,
                            categoryPhoto = imageUri ?: ""
                        )
                        categoryViewModel.modifyCategory(categoryId, updatedCategory)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bubblegum)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.save_changes),
                        fontSize = 18.sp,
                        style = GameDexTypography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomSection(navController, userViewModel, 0)
    }
}

@Preview(showBackground = true)
@Composable
fun EditCategoryScreenPreview() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    EditCategoryScreen(navController = fakeNavController, userViewModel = userViewModel)
}
