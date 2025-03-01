package cat.copernic.grup4.gamedex.Library.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Core.Model.StateType
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Core.Model.Videogame

import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import coil.compose.AsyncImage
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Library.Data.LibraryRepository
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase
import cat.copernic.grup4.gamedex.Library.UI.ViewModel.LibraryViewModel
import cat.copernic.grup4.gamedex.Library.UI.ViewModel.LibraryViewModelFactory
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.Screens.UserListScreen
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory


@Composable
fun LibraryScreen(navController: NavController, userViewModel: UserViewModel) {
    val users by userViewModel.users.collectAsState()
    val currentUser = userViewModel.currentUser.collectAsState().value
    val username = currentUser?.username ?: ""


    val context = LocalContext.current

    val libraryViewModel: LibraryViewModel = ViewModelProvider(
        context as ViewModelStoreOwner,
        LibraryViewModelFactory.LibraryViewModelFactory(LibraryUseCase(LibraryRepository()))
    ).get(LibraryViewModel::class.java)

    LaunchedEffect(username) {
        libraryViewModel.getLibrary(username)
    }

    val libraryItems by libraryViewModel.library.collectAsState()

    val onDelete: (Library) -> Unit = { libraryToDelete ->
        // Afegeix la lògica per eliminar el joc de la biblioteca
        // Ex: libraryViewModel.deleteGame(libraryToDelete)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .windowInsetsPadding(WindowInsets.systemBars),
        ) {
        header(navController, userViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.library),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ){
            items(libraryItems) { gameLibrary ->
                VideogameItem(libraryViewModel, library = gameLibrary, onDelete = onDelete)
            }
        }

        FloatingActionButton(
            onClick = { },//TODO Navegar a pantalla de afegir joc
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            containerColor = colorResource(id = R.color.buttons)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Videogame")
        }
    }
    BottomSection(navController, userViewModel, 4)
}

@Composable
fun VideogameItem(
    libraryViewModel: LibraryViewModel,
    library: Library,
    onDelete: (Library) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            val imageBitmap = library.videogame.gamePhoto?.let {
                libraryViewModel.base64ToBitmap(it)
            }
            imageBitmap?.let {
                Image(
                    it, contentDescription = "videogame picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                )
            }
            /*AsyncImage(
                model = library.videogame.gamePhoto,
                contentDescription = library.videogame.nameGame,
                modifier = Modifier.size(64.dp)
            )*/
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = library.videogame.nameGame, fontWeight = FontWeight.Bold)
                Text(text = library.videogame.category, fontSize = 14.sp, color = Color.Gray)
                Text(text = library.state.name, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onDelete(library) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Game")
            }
        }
    }
}


@Preview
@Composable
fun LibraryScreenPreview() {
    val fakeNavController = rememberNavController()
    val fakeUserRepository = UserRepository()
    val useCases = UseCases(fakeUserRepository)

    // Crea un fake ViewModel amb dades falses per la vista prèvia
    val userViewModel =
        UserViewModel(useCases) // Assegura't que UserViewModel té un constructor per defecte o crea'n un per la vista prèvia

    LibraryScreen(navController = fakeNavController, userViewModel = userViewModel)
}

