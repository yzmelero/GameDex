package cat.copernic.grup4.gamedex.Category.UI.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModel
import cat.copernic.grup4.gamedex.Category.UI.ViewModel.CategoryViewModelFactory
import androidx.navigation.NavController
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.ui.header

@Composable
fun ListCategoryScreen(navController: NavController, userViewModel: UserViewModel) {

    val categoryCases = CategoryCases(CategoryRepository())
    val categoryViewModel: CategoryViewModel =
        viewModel(factory = CategoryViewModelFactory(categoryCases))

    val query = remember { navController.currentBackStackEntry?.arguments?.getString("nameCategory") } ?: return
    var searchQuery by remember { mutableStateOf("") }
    val category by categoryViewModel.category.collectAsState()

    val currentUser by userViewModel.currentUser.collectAsState()
    val isAdmin = currentUser?.userType == UserType.ADMIN

    LaunchedEffect(query) {
        if (query.isEmpty() || query.isBlank()){
            categoryViewModel.getAllCategory()
        } else {
            categoryViewModel.filterCategories(query)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                header(navController, userViewModel)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.category),
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,

                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }

        Spacer(modifier = Modifier.height(10.dp))

            SearchBar(searchQuery, navController) { newQuery ->
                searchQuery = newQuery
            }

            Spacer(modifier = Modifier.height(10.dp))

            CategoriesGrid(category, navController)

            Spacer(modifier = Modifier.weight(1f))

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomSection(navController, userViewModel, 0)
        }

        if (isAdmin) {
            FloatingAddButton(navController)
        }
    }
}

@Composable
fun SearchBar(query: String, navController: NavController, onQueryChange: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            BasicTextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            Icon(
                modifier = Modifier.clickable { navController.navigate("list_category/$query") },
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun CategoriesGrid(category: List<Category>, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 80.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        category.forEach { category ->
            CategoryButton(
                name = category.nameCategory,
                modifier = Modifier.clickable {
                    navController.navigate("view_category/${category.nameCategory}") })
        }
    }
}


@Composable
fun CategoryButton(name: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Ombra afegida
        modifier = modifier
            .padding(top = 5.dp)
            .height(50.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun FloatingAddButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 100.dp, end = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(
            onClick = { navController.navigate("add_category") },
            modifier = Modifier
                .size(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(colorResource(R.color.header), shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_category),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListCategoryScreen() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    ListCategoryScreen(navController = fakeNavController, userViewModel)
}