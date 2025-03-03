package cat.copernic.grup4.gamedex.videogames.ui.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
fun ValidateGamesScreen(navController: NavController, userViewModel: UserViewModel) {

    val gameId = remember {
        // Obté la ID del joc dels paràmetres de navegació
        navController.currentBackStackEntry?.arguments?.getString("gameId")
    } ?: return // Si es null, surt

    //val gameId = "67c064d5c11df1548c1516e3";
    val videogameUseCase = VideogameUseCase(VideogameRepository())
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(videogameUseCase))
    val game by gameViewModel.gameById.collectAsState()

    LaunchedEffect(gameId) {
        gameViewModel.videogamesById(gameId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header(navController, userViewModel)
            game?.let { AcceptGame(it, gameViewModel, userViewModel, navController) }
        }
        BottomSection(navController,userViewModel ,1)
    }
}

@Composable
fun AcceptGame(videogame : Videogame, gameViewModel: GameViewModel, userViewModel: UserViewModel, navController: NavController) {
    Column ( modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.background))
        .windowInsetsPadding(WindowInsets.systemBars)
        .verticalScroll(rememberScrollState())
        .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.verify_game),
            fontSize = 50.sp,
            color = Color.Black,
            style = GameDexTypography.bodyLarge
        )
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = videogame.nameGame,
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = GameDexTypography.bodyLarge
                )
                Spacer(modifier = Modifier.height(12.dp))
                val imageBitmap = remember(videogame.gamePhoto) {
                    videogame.gamePhoto?.let { base64 ->
                        gameViewModel.base64ToBitmap(base64)
                    }
                }
                imageBitmap?.let {
                    Image(
                        it,
                        contentDescription = stringResource(R.string.cover),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
                Column (modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Spacer(modifier = Modifier.height(18.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.pegi),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700),
                            style = GameDexTypography.bodyLarge
                        )
                        Text(
                            text = ": ${videogame.ageRecommendation}",
                            fontSize = 20.sp,
                            style = GameDexTypography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.year),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700),
                            style = GameDexTypography.bodyLarge
                        )
                        Text(
                            text = ": ${videogame.releaseYear}",
                            fontSize = 20.sp,
                            style = GameDexTypography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.by_developer),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700),
                            style = GameDexTypography.bodyLarge
                        )
                        Text(
                            text = ": ${videogame.developer}",
                            fontSize = 20.sp,
                            style = GameDexTypography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.category),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700),
                            style = GameDexTypography.bodyLarge
                        )
                        Text(
                            text = ": ${videogame.category}",
                            fontSize = 20.sp,
                            style = GameDexTypography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.description) + ":",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.purple_700),
                        style = GameDexTypography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = videogame.descriptionGame,
                        fontSize = 20.sp,
                        style = GameDexTypography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    videogame.gameId?.let { gameViewModel.validateVideogame(it) }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text(stringResource(R.string.validate_game), color = Color.Black)
            }
            Spacer(modifier = Modifier.width(12.dp))
            var showDialog by remember { mutableStateOf(false) }
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(stringResource(R.string.delete_game), color = Color.White)
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(stringResource(R.string.confirm_delete)) },
                    text = { Text(stringResource(R.string.delete_question)) },
                    confirmButton = {
                        TextButton(onClick = {
                            gameViewModel.videogameDeleted
                            showDialog = false
                            navController.popBackStack()
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

@Preview(showBackground = true)
@Composable
fun PreviewValidateGamesScreen() {
    val fakeNavController = rememberNavController()
    val useCases = UseCases(UserRepository())
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(useCases))

    ValidateGamesScreen(navController = fakeNavController, userViewModel)
/*
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

    AcceptGame(fakeGame)*/
}