package cat.copernic.grup4.gamedex.Library.UI.Screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Core.Model.StateType
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.header
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.Library.Data.LibraryRepository
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase
import cat.copernic.grup4.gamedex.Library.UI.ViewModel.LibraryViewModel
import cat.copernic.grup4.gamedex.Library.UI.ViewModel.LibraryViewModelFactory
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Users.UI.Screens.UserListScreen
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModelFactory
import cat.copernic.grup4.gamedex.videogames.data.VideogameRepository
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModel
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModelFactory
import java.time.LocalDate
import java.util.UUID
//TODO Afegir les strings de AddGameToLibraryScreen
@Composable
fun AddGameToLibraryScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val gameId = remember {
        // Obté la ID del joc dels paràmetres de navegació
        navController.currentBackStackEntry?.arguments?.getString("gameId")
    } ?: return // Si es null, surt

    val videogameUseCase = VideogameUseCase(VideogameRepository())
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(videogameUseCase))
    val game by gameViewModel.gameById.collectAsState()

    val context = LocalContext.current


    LaunchedEffect(gameId) { Log.d("error", "gameId: ${gameId}")
        gameViewModel.videogamesById(gameId)
    }

    val users by userViewModel.users.collectAsState()

    val libraryViewModel: LibraryViewModel = ViewModelProvider(
        context as ViewModelStoreOwner,
        LibraryViewModelFactory.LibraryViewModelFactory(LibraryUseCase(LibraryRepository()))
    ).get(LibraryViewModel::class.java)

    val message by libraryViewModel.message.collectAsState()

    LaunchedEffect(message) {
        message?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            libraryViewModel.clearMessage()
        }
    }
    var selectedState by remember { mutableStateOf(context.getString(R.string.select)) }
    val stateOptions = listOf(
        context.getString(R.string.finished),
        context.getString(R.string.playing),
        context.getString(R.string.wanttoplay),
        context.getString(R.string.dropped)
    )

    var rating by remember { mutableDoubleStateOf(0.0) }
    var comment by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                header(navController, userViewModel)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Títol de la secció
            Text(text = context.getString(R.string.review), style = GameDexTypography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(colorResource(id = R.color.boxbackground), RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {

                    // Estat del videojoc
                    Text(text = context.getString(R.string.stateGame), fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween // Espai entre text i icona
                        ) {
                            Text(text = selectedState)
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown, // Icona desplegable
                                contentDescription = "Dropdown Icon",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Gray
                            )
                        }
                    }


                    DropdownMenu(
                        expanded = expanded,
                        modifier = Modifier.zIndex(1f),
                        onDismissRequest = { expanded = false }
                    ) {
                        stateOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    selectedState = option
                                    expanded = false
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Valoració
                    Text(text = context.getString(R.string.rating), fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        (1..10).forEach { index ->
                            Icon(
                                imageVector = if (index <= rating.toInt()) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Star $index",
                                tint = if (index <= rating.toInt()) Color.Yellow else Color.Gray,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clickable { rating = index.toDouble() }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Comentari
                    Text(text = context.getString(R.string.comment), fontSize = 18.sp)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp) // Li donem un espai fixe dins la box
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        BasicTextField(
                            value = comment,
                            onValueChange = { comment = it },
                            modifier = Modifier.fillMaxSize(),
                            textStyle = TextStyle(fontSize = 16.sp)

                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botó de confirmació
                    Button(
                        onClick = {
                            val user = userViewModel.currentUser.value

                            val stateEnum = getStateTypeFromString(selectedState, context)

                            //Per a que funcioni l'state en l'objecte Library, necessito passar el valor de String a StateType.


                            Log.d("AddGameToLibrary", "User: $user, /*Videogame: $game*/")
                            Log.d("AddGameToLibrary", "Llista d'usuaris: $users")

                            if (user != null && game != null) {
                                val newLibraryEntry = Library(
                                    idLibrary = UUID.randomUUID().toString(),
                                    user = user,
                                    videogame = game!!,
                                    state = stateEnum,
                                    description = comment,
                                    rating = rating,
                                    publishedDate = LocalDate.now().toString()
                                )
                                libraryViewModel.addGameToLibrary(newLibraryEntry)
                                //TODO Canviar ruta per a que porti a la biblioteca
                                navController.popBackStack()
                                Log.d("AddGameToLibrary", "Pop back stack")

                            }
                        },

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Text(text = stringResource(R.string.confirm), color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            BottomSection(navController, userViewModel, 4)
        }
    }

}

fun getStateTypeFromString(selectedState: String, context: Context): StateType {
    return when (selectedState) {
        context.getString(R.string.finished) -> StateType.FINISHED
        context.getString(R.string.playing) -> StateType.PLAYING
        context.getString(R.string.wanttoplay) -> StateType.WANTTOPLAY
        context.getString(R.string.dropped) -> StateType.DROPPED
        else -> StateType.WANTTOPLAY // Valor per defecte si no coincideix cap
    }
}

@Composable
@Preview
fun AddGameToLibraryScreenPreview() {
    val fakeNavController = rememberNavController() // ✅ Crear un NavController fals per la preview
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))

    val fakeGame = Videogame(
        nameGame = "Nombre prueba",
        releaseYear = "2022",
        category = "Categoria",
        developer = "FromSoftware",
        ageRecommendation = "18",
        descriptionGame = "Lorem ipsum dolor sit amet consectetur adipiscing elit odio aptent cubilia, laoreet cursus pharetra vulputate pellentesque integer nec fermentum sociis id, feugiat class torquent vel egestas primis mus sed fusce. Interdum condimentum mauris sed ridiculus duis justo phasellus, lobortis feugiat augue ultricies cum ultrices arcu ullamcorper, curabitur in cras auctor morbi sapien. Consequat penatibus litora tristique dis rutrum nec venenatis aliquam, lectus aptent laoreet fames condimentum augue varius gravida metus, montes platea duis conubia justo quis lobortis.",
        gamePhoto = "",
        gameId = "1"
    )

    AddGameToLibraryScreen(navController = fakeNavController, userViewModel = userViewModel)
}