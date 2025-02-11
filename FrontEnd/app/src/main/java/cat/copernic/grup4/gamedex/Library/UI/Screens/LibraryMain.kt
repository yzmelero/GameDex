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
import androidx.compose.ui.tooling.preview.Preview
import cat.copernic.grup4.gamedex.R

@Composable
fun Library(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
            .padding(16.dp)
    ) {
    Text(
        text = "Library",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    LazyColumn {
        items(videogames) { videogame ->
            VideogameItem(videogame, onDelete)
        }
    }
    FloatingActionButton(
        onClick = {},
        modifier = Modifier
            .align(Alignment.End)
            .padding(16.dp),
        containerColor = colorResource(id = R.color.buttons)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Videogame")
    }}
}

@Composable
fun VideogameItem(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Row(modifier = Modifier.padding(16.dp)){
            AsyncImage(
                model = videogame.imageUrl,
                contentDescription = game.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = game.name, fontWeight = FontWeight.Bold)
                Text(text = game.genre, fontSize = 14.sp, color = Color.Gray)
                Text(text = game.status.name, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {onDelete(videogame)}) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Game")
            }
        }
    }
}

@Preview
@Composable
fun LibraryPreview(){
    Library()
}


