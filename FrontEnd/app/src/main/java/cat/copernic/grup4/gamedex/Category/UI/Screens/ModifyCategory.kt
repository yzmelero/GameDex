package cat.copernic.grup4.gamedex.Categories.UI.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModel
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModelFactory
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel

@Composable
fun ModifyCategoryScreen(navController: NavController, userViewModel: UserViewModel) {
    val categoryCases = CategoryCases(CategoryRepository())
    val categoryViewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelFactory(categoryCases))

    val context = LocalContext.current

    val categoryId = remember {
        navController.currentBackStackEntry?.arguments?.getString("categoryId")
    } ?: return

    val category by categoryViewModel.categoryGetById.collectAsState()
    val updateSuccess by categoryViewModel.categoryModified.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(categoryId) {
        Log.d("ModifyCategoryScreen", "Fetching category with ID: $categoryId")
        categoryViewModel.getCategoryById(categoryId)
    }

    LaunchedEffect(category) {
        category?.let {
            Log.d("ModifyCategoryScreen", "Loaded category: ${it.nameCategory}, ${it.description}")
            name = it.nameCategory
            description = it.description
        } ?: Log.d("ModifyCategoryScreen", "Category is null")
    }

    DisposableEffect(Unit) {
        onDispose {
            Log.d("ModifyCategoryScreen", "Resetting categoryModified state")
            categoryViewModel._categoryModified.value = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        header(navController, userViewModel)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.modify_category),
                fontSize = 40.sp,
                color = Color.Black,
                style = GameDexTypography.bodyLarge
            )

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = name,
                        fontSize = 24.sp,
                        color = Color.Black,
                        style = GameDexTypography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    InputFieldEdit(label = stringResource(R.string.description), value = description) {
                        description = it
                        Log.d("ModifyCategoryScreen", "Updated description: $description")
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            Log.d("ModifyCategoryScreen", "Modify button clicked")

                            val updatedCategory = category?.copy(description = description)

                            if (updatedCategory != null) {
                                Log.d("ModifyCategoryScreen", "Updating category: ${updatedCategory.nameCategory}")
                                categoryViewModel.modifyCategory(updatedCategory)
                            } else {
                                Log.d("ModifyCategoryScreen", "Error: Category is null, cannot update")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                    ) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(updateSuccess) {
        updateSuccess?.let { success ->
            if (success) {
                Log.d("ModifyCategoryScreen", "Category updated successfully")
                Toast.makeText(
                    context,
                    context.getString(R.string.category_updated),
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate("categories")
            } else {
                Log.d("ModifyCategoryScreen", "Error updating category")
                Toast.makeText(
                    context,
                    context.getString(R.string.error_updating_category),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    BottomSection(navController, userViewModel, 0)
}

@Composable
fun InputFieldEdit(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = label, color = Color.Black, fontSize = 14.sp)
        TextField(
            value = value,
            placeholder = { Text(text = stringResource(R.string.leave_empty)) },
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}
