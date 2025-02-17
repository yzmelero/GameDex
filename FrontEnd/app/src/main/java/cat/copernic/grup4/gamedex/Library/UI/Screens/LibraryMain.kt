package cat.copernic.grup4.gamedex.Library.UI.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Core.Model.StateType
import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.videogames.ui.screen.HeaderSection
import coil.compose.AsyncImage


@Composable
fun Library(library: List<Library>, onDelete: (Videogame) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp)
    ) {
        HeaderSection()

        Text(
            text = stringResource(R.string.library),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(library) { library ->
                VideogameItem(library, onDelete)
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
    BottomSection()
}

@Composable
fun VideogameItem(library: Library, onDelete: (Library) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = library.videogame.gamePhoto,
                contentDescription = library.videogame.nameGame,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = library.videogame.nameGame, fontWeight = FontWeight.Bold)
                Text(text = library.videogame.nameCategory, fontSize = 14.sp, color = Color.Gray)
                Text(text = library.state.name, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onDelete(library) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Game")
            }
        }
    }
}

@Composable
fun BottomSection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Bottom
    ) {
        BottomNavBar(selectedItem = 4, onItemSelected = {})
    }
}

@Preview
@Composable
fun LibraryPreview() {

}


