package cat.copernic.grup4.gamedex.videogames.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Core.Model.UserType
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.Library.Data.LibraryRepository
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase
import cat.copernic.grup4.gamedex.Library.UI.ViewModel.LibraryViewModel
import cat.copernic.grup4.gamedex.Library.UI.ViewModel.LibraryViewModelFactory
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
fun ViewGamesScreen(navController: NavController, userViewModel: UserViewModel) {

    val gameId = remember {
        // Obté la ID del joc dels paràmetres de navegació
        navController.currentBackStackEntry?.arguments?.getString("gameId")
    } ?: return // Si es null, surt

    val videogameUseCase = VideogameUseCase(VideogameRepository())
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(videogameUseCase))
    val game by gameViewModel.gameById.collectAsState()
    val context = LocalContext.current
    val videogameDeleted by gameViewModel.videogameDeleted.collectAsState()

    val libraryViewModel: LibraryViewModel = ViewModelProvider(
        context as ViewModelStoreOwner,
        LibraryViewModelFactory.LibraryViewModelFactory(LibraryUseCase(LibraryRepository()))
    ).get(LibraryViewModel::class.java)

    val comment by libraryViewModel.comments.collectAsState()

    val rating by libraryViewModel.rating.collectAsState()

    val currentUser by userViewModel.currentUser.collectAsState()

    LaunchedEffect(videogameDeleted) {
        if (videogameDeleted == true) {
            Toast.makeText(context,
                context.getString(R.string.videogame_deleted_succesfully), Toast.LENGTH_SHORT).show()
            navController.navigate("listVideogames")
        }
    }

    LaunchedEffect(gameId) {
        gameViewModel.videogamesById(gameId)
        libraryViewModel.getCommentsByGame(gameId, context)
        libraryViewModel.getAverageRating(gameId, context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header(navController, userViewModel)
            game?.let { GameCard(it, gameViewModel, userViewModel, navController, comment, rating, currentUser, context, libraryViewModel) }
        }
        BottomSection(navController, userViewModel, 1)
    }
}

@Composable
fun GameCard(
    videogame: Videogame,
    gameViewModel: GameViewModel,
    userViewModel: UserViewModel,
    navController: NavController,
    comment: List<Library>,
    rating: Double?,
    currentUser: User?,
    context: Context,
    libraryViewModel: LibraryViewModel
) {
    val currentUser = userViewModel.currentUser.collectAsState().value
    val isAdmin = currentUser?.userType == UserType.ADMIN


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.addgame_library),
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color.Magenta, shape = RoundedCornerShape(24))
                            .clickable {
                                val gameId = videogame.gameId
                                Log.d(
                                    "AddGameToLibraryScreen",
                                    "Navigating to game with ID: $gameId"
                                )
                                gameId?.let { navController.navigate("addToLibrary/$it") }

                            }
                    )
                }
                Row(verticalAlignment = Alignment.Top) {
                    val imageBitmap = remember(videogame.gamePhoto) {
                        videogame.gamePhoto?.let { base64 ->
                            gameViewModel.base64ToBitmap(base64)
                        }
                    }

                    imageBitmap?.let {
                        Image(
                            it,
                            contentDescription = stringResource(R.string.cover),
                            modifier = Modifier.size(height = 210.dp, width = 180.dp)
                        )
                    }
                    Column {
                        Text(
                            text = videogame.nameGame,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            style = GameDexTypography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = ("⭐ ${rating ?: "N/A"} ⭐"),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.yellowdark),
                            style = GameDexTypography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Row {
                            Text(
                                text = stringResource(R.string.pegi),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.purple_700),
                                style = GameDexTypography.bodyLarge
                            )
                            Text(
                                text = ": ${videogame.ageRecommendation}",
                                fontSize = 18.sp,
                                style = GameDexTypography.bodyLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(
                                text = stringResource(R.string.year),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.purple_700),
                                style = GameDexTypography.bodyLarge
                            )
                            Text(
                                text = ": ${videogame.releaseYear}",
                                fontSize = 18.sp,
                                style = GameDexTypography.bodyLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(
                                text = stringResource(R.string.category),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.purple_700),
                                style = GameDexTypography.bodyLarge
                            )
                            Text(
                                text = ": ${videogame.category}",
                                fontSize = 18.sp,
                                style = GameDexTypography.bodyLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(
                                text = stringResource(R.string.by_developer),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.purple_700),
                                style = GameDexTypography.bodyLarge
                            )
                            Text(
                                text = ": ${videogame.developer}",
                                fontSize = 18.sp,
                                style = GameDexTypography.bodyLarge
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.description) + ":",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.purple_700),
                        style = GameDexTypography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = videogame.descriptionGame,
                        fontSize = 18.sp,
                        style = GameDexTypography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if(isAdmin) {
                    Button(
                        onClick = { navController.navigate("updateVideogame/${videogame.gameId}") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            stringResource(R.string.modify),
                            fontSize = 20.sp,
                            style = GameDexTypography.bodyLarge
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 12.dp, end = 12.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (currentUser?.userType == UserType.ADMIN) {
                    var showDialog by remember { mutableStateOf(false) }

                    IconButton(
                        onClick = { showDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete),
                            modifier = Modifier.size(30.dp)
                                .background(Color.Red, shape = RoundedCornerShape(50))
                        )
                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(stringResource(R.string.confirm_delete)) },
                            text = { Text(stringResource(R.string.delete_question)) },
                            confirmButton = {
                                TextButton(onClick = {
                                    videogame.gameId?.let {
                                        gameViewModel.deleteVideogame(it)
                                        showDialog = false
                                        navController.popBackStack()
                                    } ?: Log.e("DELETE_GAME", "Error: gameId is null or empty")
                                }) {
                                    Text(stringResource(R.string.delete), color = Color.Red)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                }) { Text(stringResource(R.string.cancel)) }
                            }
                        )
                    }
                }
            }
        }
        CommentsSection(videogame.gameId.toString(), comment, navController, currentUser, context, libraryViewModel)
    }
}

@Composable
fun CommentsSection(gameId: String, comment: List<Library>, navController: NavController, currentUser: User?, context: Context, libraryViewModel: LibraryViewModel) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(16.dp)
            .padding(bottom = 80.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.comments),
                    fontSize = 28.sp,
                    style = GameDexTypography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_comment),
                    Modifier
                        .clickable {
                            Log.d(
                                "AddGameToLibraryScreen",
                                "Navigating to game with ID: $gameId"
                            )
                            gameId?.let { navController.navigate("addToLibrary/$it") }
                        }
                        .background(Color.Magenta, shape = RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            comment.forEach { library ->
                if (library.description.isNotBlank() && library.description.isNotEmpty()){
                CommentItem(
                    username = library.user.username,
                    comment = library.description ?: "No description",
                    rating = "⭐ ${library.rating} ⭐",
                    currentUser,
                    context,
                    libraryViewModel,
                    gameId,
                    navController
                )
            }
            }
        }
    }
}

@Composable
fun CommentItem(username: String, comment: String, rating: String, currentUser: User?, context: Context, libraryViewModel: LibraryViewModel, gameId: String, navController: NavController) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = username,
                fontSize = 24.sp,
                style = GameDexTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = comment,
                fontSize = 18.sp,
                style = GameDexTypography.bodyLarge
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = rating,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(width = 80.dp, height = 30.dp)
                        .clip(RoundedCornerShape(40))
                        .background(colorResource(R.color.bubblegum))
                        .padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                if (currentUser?.userType == UserType.ADMIN) {
                    var showDialog by remember { mutableStateOf(false) }

                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete),
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.Red)
                    ) }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(stringResource(R.string.confirm_delete)) },
                            text = { Text(stringResource(R.string.delete_question)) },
                            confirmButton = {
                                TextButton(onClick = {
                                    libraryViewModel.deleteVideogameFromLibrary(gameId, username, context)
                                    showDialog = false
                                    navController.navigate("viewGame/${gameId}")
                                }) {
                                    Text(stringResource(R.string.delete), color = Color.Red)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDialog = false
                                }) { Text(stringResource(R.string.cancel)) }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewViewGamesScreen() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))

    ViewGamesScreen(navController = fakeNavController, userViewModel)


}