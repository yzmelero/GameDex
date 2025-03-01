package cat.copernic.grup4.gamedex.Category.UI.Screens

import android.widget.Toast
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
import cat.copernic.grup4.gamedex.Core.Model.UserType
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

    val context = LocalContext.current
    val categoryDeleted by categoryViewModel.categoryDeleted.collectAsState()

    LaunchedEffect(categoryDeleted) {
        if (categoryDeleted == true) {
            Toast.makeText(context, R.string.succDelCat, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    val currentCategory = category
    val currentUser = userViewModel.currentUser.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFDBB2FF), Color(0xFFF7E6FF))
                )
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header(navController, userViewModel)

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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

                        currentCategory?.let {
                            val imageBitmap = it.categoryPhoto?.let { userViewModel.base64ToBitmap(it) }
                            imageBitmap?.let { bitmap ->
                                Image(
                                    bitmap,
                                    contentDescription = stringResource(R.string.photo_category),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        currentCategory?.let {
                            Text(
                                it.description,
                                fontSize = 18.sp,
                                style = GameDexTypography.bodyLarge,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if (currentUser?.userType == UserType.ADMIN) {
                            Button(
                                onClick = { navController.navigate("/modify_category/${currentCategory?.nameCategory}") },
                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bubblegum)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    stringResource(R.string.modify),
                                    fontSize = 18.sp,
                                    style = GameDexTypography.bodyLarge
                                )
                            }
                        }
                    }

                    if (currentUser?.userType == UserType.ADMIN) {
                        var showDialog by remember { mutableStateOf(false) }

                        IconButton(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .background(Color.Red, shape = CircleShape)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete_category), tint = Color.White)
                        }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text(stringResource(R.string.confirm_delete)) },
                                text = { Text(stringResource(R.string.delete_question)) },
                                confirmButton = {
                                    TextButton(onClick = {
                                        categoryViewModel.deleteCategory(currentCategory?.nameCategory ?: "")
                                        showDialog = false
                                        navController.popBackStack()
                                    }) {
                                        Text(stringResource(R.string.delete), color = Color.Red)
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialog = false }) { Text(stringResource(R.string.cancel)) }
                                }
                            )
                        }
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
