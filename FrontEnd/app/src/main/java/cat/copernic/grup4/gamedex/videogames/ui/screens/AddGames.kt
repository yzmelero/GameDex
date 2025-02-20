package cat.copernic.grup4.gamedex.videogames.ui.screens


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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.ui.header
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Core.ui.BottomSection
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.videogames.data.VideogameRepository
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModel
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModelFactory

@Composable
fun AddGamesScreen(navController : NavController) {
    val videogameUseCase = VideogameUseCase(VideogameRepository())
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(videogameUseCase))

    var nameGame by remember { mutableStateOf("") }
    var releaseYear by remember { mutableStateOf("") }
    var ageRecommendation by remember { mutableStateOf("") }
    var developer by remember { mutableStateOf("") }
    var nameCategory by remember { mutableStateOf("") }
    var descriptionGame by remember { mutableStateOf("") }

    val context = LocalContext.current
    val createdGameState by gameViewModel.videogameCreated.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.background))
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header(navController)

            AddContentSection(
                nameGame, { nameGame = it },
                releaseYear, { releaseYear = it },
                ageRecommendation, { ageRecommendation = it },
                developer, { developer = it },
                nameCategory, { nameCategory = it },
                descriptionGame, { descriptionGame = it }
            )

                }
        BottomSection(navController, 1)
    }
    LaunchedEffect(createdGameState) {
        createdGameState?.let { success ->
            if (success) {
                Toast.makeText(context, R.string.gameCreated, Toast.LENGTH_LONG).show()
                navController.navigate("listvideogames")
            } else {
                Toast.makeText(context, R.string.gameErrorCreate, Toast.LENGTH_LONG).show()
            }
        }
    }
}


@Composable
fun AddContentSection(
    nameGame: String, onNameChange: (String) -> Unit,
    releaseYear: String, onReleaseYearChange: (String) -> Unit,
    ageRecommendation: String, onAgeChange: (String) -> Unit,
    developer: String, onDeveloperChange: (String) -> Unit,
    nameCategory: String, onCategoryChange: (String) -> Unit,
    descriptionGame: String, onDescriptionChange: (String) -> Unit
) {
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(VideogameUseCase(VideogameRepository())))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .background(colorResource(R.color.background))
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.add_game),
            fontSize = 22.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            AddGameFormFields(
                nameGame, onNameChange,
                releaseYear, onReleaseYearChange,
                ageRecommendation, onAgeChange,
                developer, onDeveloperChange,
                nameCategory, onCategoryChange,
                descriptionGame, onDescriptionChange
            )
            Button(
                onClick = {
                    val newGame = Videogame(
                        gameId = "",
                        nameGame = nameGame,
                        releaseYear = releaseYear,
                        ageRecommendation = ageRecommendation,
                        developer = developer,
                        nameCategory = nameCategory,
                        descriptionGame = descriptionGame,
                        gamePhoto = null
                    )
                    gameViewModel.createVideogame(newGame)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(text = stringResource(R.string.confirm), color = Color.White)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun AddGameFormFields(
    nameGame: String, onNameChange: (String) -> Unit,
    releaseYear: String, onReleaseYearChange: (String) -> Unit,
    ageRecommendation: String, onAgeChange: (String) -> Unit,
    developer: String, onDeveloperChange: (String) -> Unit,
    nameCategory: String, onCategoryChange: (String) -> Unit,
    descriptionGame: String, onDescriptionChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = nameGame,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.game_name)) },
            modifier = textFieldModifier()
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = releaseYear,
            onValueChange = onReleaseYearChange,
            label = { Text(stringResource(R.string.release_year)) },
            modifier = textFieldModifier()
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = ageRecommendation,
            onValueChange = onAgeChange,
            label = { Text(stringResource(R.string.age_recommendation)) },
            modifier = textFieldModifier()
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = developer,
            onValueChange = onDeveloperChange,
            label = { Text(stringResource(R.string.developer)) },
            modifier = textFieldModifier()
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = nameCategory,
            onValueChange = onCategoryChange,
            label = { Text(stringResource(R.string.category)) },
            modifier = textFieldModifier()
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = descriptionGame,
            onValueChange = onDescriptionChange,
            label = { Text(stringResource(R.string.description)) },
            modifier = textFieldModifier()
        )
        Spacer(modifier = Modifier.height(20.dp))
        ImagePicker()
    }
}

@Composable
fun textFieldModifier() = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))

@Composable
fun ImagePicker() {
    Row {
        Text(
            text = stringResource(R.string.cover) + ":",
            fontSize = 14.sp,
            color = Color.Black
        )
        Image(
            painter = painterResource(R.drawable.eldenring),
            contentDescription = stringResource(R.string.cover),
            modifier = Modifier
                .size(120.dp)
                .clickable { /* Acción para elegir imagen */ }
        )
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_avatar),
            modifier = Modifier
                .clickable { /* Acción para elegir imagen */ }
                .padding(top = 60.dp, start = 10.dp)
                .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
                .clip(RoundedCornerShape(50))
                .size(30.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddGamesScreen() {
    val fakeNavController = rememberNavController()
    AddGamesScreen(navController = fakeNavController)
}