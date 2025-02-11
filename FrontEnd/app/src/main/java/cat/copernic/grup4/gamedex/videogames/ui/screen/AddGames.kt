package cat.copernic.grup4.gamedex.videogames.ui.screen


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.R

@Composable
fun AddGamesScreen() {
    var nameGame by remember { mutableStateOf("") }
    var releaseYear by remember { mutableStateOf("") }
    var ageRecomendation by remember { mutableStateOf("") }
    var developer by remember { mutableStateOf("") }
    var nameCategory by remember { mutableStateOf("") }
    var descriptionGame by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .background(colorResource(R.color.background))
                .windowInsetsPadding(WindowInsets.systemBars)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "GDEX", fontSize = 24.sp, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Add Games", fontSize = 22.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = nameGame,
                        onValueChange = { nameGame = it },
                        label = { Text("Game Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = releaseYear,
                        onValueChange = { releaseYear = it },
                        label = { Text("Release Year") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = ageRecomendation,
                        onValueChange = { ageRecomendation = it },
                        label = { Text("Age Recommendation") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = developer,
                        onValueChange = { developer = it },
                        label = { Text("Developer") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = nameCategory,
                        onValueChange = { nameCategory = it },
                        label = { Text("Category") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = descriptionGame,
                        onValueChange = { descriptionGame = it },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row {
                            Text(text = "Cover:", fontSize = 14.sp, color = Color.Black)
                            Image(
                                painter = painterResource(id = R.drawable.coche),
                                contentDescription = "Cover",
                                modifier = Modifier
                                    .size(120.dp)
                                    //TODO accion para elegir imagen
                                    .clickable { /* Acción para elegir imagen */ }
                            )
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.add_avatar),
                                modifier = Modifier
                                    .clickable { /*TODO Acción para elegir imagen */ }
                                    .padding(top = 60.dp, start = 10.dp)
                                    .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
                                    .clip(RoundedCornerShape(50))
                                    .size(30.dp)
                            )
                        }

                    }
                    Button(
                        //TODO accion de registro
                        onClick = { /* Acción de registro */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Text(text = stringResource(R.string.confirm), color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavBar(selectedItem = 1, onItemSelected = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddGamesScreen() {
    AddGamesScreen()
}