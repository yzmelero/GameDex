package cat.copernic.grup4.gamedex.Category.UI.Screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.grup4.gamedex.R

@Composable
fun ListCategoryScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val categories = listOf(
        "ALL", "ACTION", "ADVENTURE", "SHOOTER", "RPG", "MOBA",
        "FANTASY", "ARCADE", "VR", "STRATEGY", "FIGHT", "SURVIVAL",
        "OPEN WORLD", "SIMULATION"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background)), // Fons degradat rosa-morat
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar()

        Spacer(modifier = Modifier.height(10.dp))

        SearchBar(searchQuery) { searchQuery = it }

        Spacer(modifier = Modifier.height(10.dp))

        CategoriesGrid(categories)

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBar(onItemSelected = {})
    }
}

@Composable
fun TopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.header))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "GDEX",
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
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
                contentDescription = "Search",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun CategoriesGrid(categories: List<String>) {
    Column(modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(top = 16.dp)) {
        categories.chunked(2).forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaiat entre botons
            ) {
                rowCategories.forEach { category ->
                    CategoryButton(category, Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CategoryButton(name: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(6.dp), // Forma rectangular amb mínim d'arrodoniment
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Ombra afegida
        modifier = modifier
            .padding(top = 5.dp)
            .height(50.dp) // Més alçada per semblar-se més a la imatge
            .clickable { /* Acció de selecció */ }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun BottomNavBar(selectedItem: Int = 0, onItemSelected: (Int) -> Unit) {
    NavigationBar (
        containerColor = Color(0xFF9D6EDB)
    ) {
        val icons = listOf(R.drawable.apps, R.drawable.gamepad, R.drawable.users_alt, R.drawable.user, R.drawable.book_open_cover)

        icons.forEachIndexed { index, iconRes ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp))
                },
                selected = selectedItem == index,
                onClick = { onItemSelected(index)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.White
                ),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListCategoryScreen() {
    ListCategoryScreen()
}