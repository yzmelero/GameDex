package cat.copernic.grup4.gamedex.videogames.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.videogames.ui.viewmodel.GameViewModel

@Composable
fun ViewGamesScreen(viewModel: GameViewModel, gameId: String) {

    LaunchedEffect(gameId) {
        viewModel.videogamesById(gameId)
    }

    val game by viewModel.gameById.collectAsState()

    var nameGame by remember { mutableStateOf("") }
    var releaseYear by remember { mutableStateOf("") }
    var ageRecommendation by remember { mutableStateOf("") }
    var developer by remember { mutableStateOf("") }
    var nameCategory by remember { mutableStateOf("") }
    var descriptionGame by remember { mutableStateOf("") }

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
            HeaderSection()

            game?.let { GameCard(it) }

        }
        BottomSection()
    }
}

@Composable
fun GameCard(videogame : Videogame) {
    Column ( modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 80.dp)
        .background(colorResource(R.color.background))
        .windowInsetsPadding(WindowInsets.systemBars)
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally){
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
                    modifier = Modifier.size(30.dp)
                        .background(Color.Magenta, shape = RoundedCornerShape(24))
                )
            }
            Row(verticalAlignment = Alignment.Top) {
                Image(
                    painter = painterResource(R.drawable.eldenring),
                    contentDescription = stringResource(R.string.cover),
                    modifier = Modifier.size(180.dp)
                )
                Column {
                    Text(
                        text = videogame.nameGame,
                        fontSize = 22.sp, fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "⭐ 7.85 ⭐",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.yellowdark)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.pegi),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700)
                        )
                        Text(
                            text = ": ${videogame.ageRecommendation}",
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.year),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700)
                        )
                        Text(
                            text = ": ${videogame.releaseYear}",
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.game_category),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700)
                        )
                        Text(
                            text = ": ${videogame.nameCategory}",
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.by_developer),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.purple_700)
                        )
                        Text(
                            text = ": ${videogame.developer}",
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = videogame.descriptionGame,
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Acción de Modificar */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(stringResource(R.string.modify))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp, end = 12.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.addgame_library),
                modifier = Modifier.size(30.dp)
                    .background(Color.Red, shape = RoundedCornerShape(50))
            )
        }
    }
    CommentsSection()

    }
}

@Composable
fun CommentsSection() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.comments), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_comment),
                    Modifier
                        .clickable { /* Acción para elegir imagen */ }
                        .background(Color.Magenta, shape = RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "VicoGracias" /* Nom usuari comentari */,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Bruh, Elden Ring is mad fire, fam. Big bosses, sick world, and magic that's straight lit. 🔥" /* comentari del usuari */
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
                            text = "⭐9.96⭐",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .size(width = 80.dp, height = 30.dp)
                                .background(colorResource(R.color.bubblegum), shape = RoundedCornerShape(40))
                                .padding(top = 4.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.addgame_library),
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color.Red, shape = RoundedCornerShape(50))
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
    val fakeViewModel = GameViewModel() // Simula un ViewModel para la preview
    ViewGamesScreen(viewModel = fakeViewModel, gameId = "1")
    /*
    val testGame = Videogame( /* test per mostrar dades del joc, encara no hi ha dades a la bbdd */
        nameGame = "Elden Ring",
        releaseYear = "2022",
        nameCategory = "RPG",
        developer = "FromSoftware",
        ageRecommendation = "18",
        descriptionGame = "Elden Ring is an action RPG which takes place in the Lands Between, sometime after the Shattering of the titular Elden Ring. Players must explore and fight their way through the vast open-world to unite all the shards, restore the Elden Ring, and become Elden Lord.",
        gamePhoto = painterResource(R.drawable.eldenring).toString(),
        gameId = 1
    )
    ViewGamesScreen(testGame)*/
}