package cat.copernic.grup4.gamedex.Category.UI.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R

@Composable
fun ListCategoryScreen(/*navController: NavController*/) {
    var searchQuery by remember { mutableStateOf("") }
    val categories = listOf(
        "ALL", "ACTION", "ADVENTURE", "SHOOTER", "RPG", "MOBA",
        "FANTASY", "ARCADE", "VR", "STRATEGY", "FIGHT", "SURVIVAL",
        "OPEN WORLD", "SIMULATION"
    )

    Box(modifier = Modifier.fillMaxSize()) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopBar(onLogoutClick = {}, profileImageRes = R.drawable.coche)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.category),
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,

                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        SearchBar(searchQuery) { searchQuery = it }

        Spacer(modifier = Modifier.height(10.dp))

        CategoriesGrid(categories)

        Spacer(modifier = Modifier.weight(1f))

    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavBar(onItemSelected = {})
        }
        FloatingAddButton(onClick = {})
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
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 80.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            CategoryButton(category, Modifier.fillMaxWidth()) // Ocupa todo el ancho disponible
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
            .height(50.dp)
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
fun FloatingAddButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 100.dp, end = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(56.dp)
                .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_category),
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