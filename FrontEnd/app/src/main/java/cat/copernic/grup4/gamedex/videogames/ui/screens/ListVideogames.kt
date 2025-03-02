package cat.copernic.grup4.gamedex.videogames.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import cat.copernic.grup4.gamedex.videogames.data.VideogameRepository
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModel
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModelFactory

@Composable
fun ListGamesScreen(navController : NavController, userViewModel: UserViewModel) {
    val videogameUseCase = VideogameUseCase(VideogameRepository())
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(videogameUseCase))
    val videogame by gameViewModel.allVideogame.collectAsState()
    val categories by gameViewModel.categories.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(Unit) {
        gameViewModel.getAllVideogames()
        gameViewModel.getAllCategories()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header(navController, userViewModel)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.list_game),
                fontSize = 50.sp,
                color = Color.Black,
                style = GameDexTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(searchQuery) { searchQuery = it }
            CategoryFilter(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                    Log.d("ListGamesScreen", "Categoría seleccionada: ${it.nameCategory}")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            VideogamesGrid(videogame, navController, gameViewModel)

        }
        BottomSection(navController, userViewModel,1)
        GameButtons(navController, userViewModel)
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    // TODO fer el filtre i la recerca funcional, es provisional
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
                onValueChange = onQueryChange,
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = R.string.search.toString(),
                tint = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilter(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        // Dropdown amb el valor seleccionat
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedCategory?.nameCategory ?: context.getString(R.string.selectCategory),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                trailingIcon = { TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.nameCategory) },
                        onClick = {
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun VideogamesGrid(videogame: List<Videogame>, navController: NavController, gameViewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 80.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        videogame.sortedBy { it.nameGame }.forEach { game ->
            GameItem(videogame = game, navController = navController, gameViewModel = gameViewModel)
        }
    }
}

@Composable
fun GameItem(videogame: Videogame, navController: NavController, gameViewModel: GameViewModel) {
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("viewGame/${videogame.gameId}") }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageBitmap = remember(videogame.gamePhoto) {
                videogame.gamePhoto?.let { base64 ->
                    gameViewModel.base64ToBitmap(base64)
                }
            }

            imageBitmap?.let {
                Image(
                    it,
                    contentDescription = videogame.nameGame,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = videogame.nameGame,
                    fontSize = 28.sp,
                    style = GameDexTypography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = videogame.category,
                    fontSize = 26.sp,
                    color = Color.DarkGray,
                    style = GameDexTypography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.width(74.dp))
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(onClick = {
                    navController.navigate("viewGame/${videogame.gameId}")
                }) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Magenta, shape = RoundedCornerShape(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.eye),
                            contentDescription = stringResource(R.string.view_game),
                            modifier = Modifier
                                .size(38.dp)
                                .padding(6.dp)
                        )
                    }
                }
            }
        }
    }

}
@Composable
fun GameButtons(navController: NavController, userViewModel: UserViewModel) {
    val currentUser by userViewModel.currentUser.collectAsState()
    val isAdmin = currentUser?.userType == UserType.ADMIN
    Row(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 100.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        if (isAdmin) {
        IconButton(
            onClick = {navController.navigate("listInactiveGames")},
            modifier = Modifier
                .size(56.dp)
                .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.valide_game),
                modifier = Modifier.size(40.dp)
            )
        }
            }
        IconButton(
            onClick = {navController.navigate("addGames")},
            modifier = Modifier
                .size(56.dp)
                .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_game),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewListGamesScreen() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))
    ListGamesScreen(navController = fakeNavController, userViewModel)
}