package cat.copernic.grup4.gamedex.Category.UI.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModel
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModelFactory
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import cat.copernic.grup4.gamedexandroid.Core.Model.Category
import coil.compose.rememberAsyncImagePainter

@Composable
fun AddCategoryScreen(navController: NavController, userViewModel: UserViewModel) {

    val categoryCases = CategoryCases(CategoryRepository())
    val categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModelFactory(categoryCases))

    var categoryName by remember { mutableStateOf("") }
    var categoryDescription by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val categoryAdded by categoryViewModel.categoryAdded.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri = it }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(navController, profileImageRes = R.drawable.user)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.add_category),
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(1f)
                    .padding(bottom = 100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .verticalScroll(rememberScrollState()),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(label = stringResource(R.string.name_category), text = categoryName) { categoryName = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(label = stringResource(R.string.description), text = categoryDescription, height = 180.dp) { categoryDescription = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(stringResource(R.string.image_category), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(20.dp))
                    ImageUploadSection(imageUri) { imagePickerLauncher.launch("image/*") }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    onClick = {
                            val newCategory = Category(
                                nameCategory = categoryName,
                                description = categoryDescription,
                                categoryPhoto = categoryViewModel.uriToBase64(context, imageUri!!).toString()
                            )
                            categoryViewModel.addCategory(newCategory)
                        },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),

                    ) {
                    Text(text = stringResource(id = R.string.confirm), color = Color.White,  fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomSection(navController, userViewModel,0)
        }
    }

    LaunchedEffect(categoryAdded) {
        categoryAdded?.let { success ->
            if (success) {
                Toast.makeText(context, context.getString(R.string.category_added), Toast.LENGTH_LONG).show()
                navController.navigate("list_category") {
                    popUpTo("add_category") { inclusive = true }
                }
            } else {
                Toast.makeText(context, context.getString(R.string.category_not_created), Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun TextField(label: String, text: String, height: Dp = 80.dp, onTextChanged: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChanged,
        label = { Text(
            label,
            modifier = Modifier
                .padding(bottom = 10.dp),
            fontSize = 16.sp,
        ) },
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(15.dp))
            .padding(8.dp),
    )
}

@Composable
fun ImageUploadSection(imageUri: Uri?, onImageClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        val painter = if (imageUri != null) {
            rememberAsyncImagePainter(imageUri)
        } else {
            painterResource(id = R.drawable.coche)
        }
        Image(
            painter = painter,
            contentDescription = "Image select",
            modifier = Modifier.fillMaxSize()
        )
        IconButton(
            onClick = { onImageClick() },
            modifier = Modifier
                .size(48.dp)
                .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_category),
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddCategoryScreen() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    AddCategoryScreen(navController = fakeNavController, userViewModel)
}
