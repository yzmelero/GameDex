package cat.copernic.grup4.gamedex.Core.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.copernic.grup4.gamedex.R

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
fun PreviewBottomBar() {
    BottomNavBar(selectedItem = 0, onItemSelected = {})}
