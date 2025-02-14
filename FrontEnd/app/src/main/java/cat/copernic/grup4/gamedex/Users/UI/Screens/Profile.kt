package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R

@Composable
fun ProfileScreen() {
    var username by remember { mutableStateOf("Juan") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFDBB2FF), Color(0xFFF7E6FF))
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Header
        HeaderSection()

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.coche),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.profile_picture),
            modifier = Modifier
                .size(320.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Username
        Text(username, fontSize = 56.sp,
            style = GameDexTypography.bodyLarge,
            color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        // Stats Section
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            //TODO: hacer un count con los datos de cada categoria para los numeros
            StatItem(stringResource(R.string.completed), "85")
            StatItem(stringResource(R.string.playing), "8")
            StatItem(stringResource(R.string.pending), "20")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Library Button
        Button(
            onClick = { /* TODO: Hacer el navigation a la library */ },
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(stringResource(R.string.library),
                color = Color.White, fontSize = 18.sp,
                style = GameDexTypography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomSection()
    }
}

@Composable
fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(onLogoutClick = {}, profileImageRes = R.drawable.coche)

    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 32.sp,
            style = GameDexTypography.bodyLarge,
            color = Color.Black)
        Text(value, fontSize = 28.sp,
            style = GameDexTypography.bodyLarge,
            color = Color.Gray)
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
        BottomNavBar(selectedItem = 1, onItemSelected = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

