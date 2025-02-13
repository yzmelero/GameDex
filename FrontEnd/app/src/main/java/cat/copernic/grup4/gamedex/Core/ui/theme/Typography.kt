package cat.copernic.grup4.gamedex.Core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.grup4.gamedex.R

val Jersey = FontFamily(
    Font(R.font.jersey_15_latin_400_normal),
    Font(R.font.jersey_15_latin_ext_400_normal)
)

val GameDexTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Jersey,
        fontSize = 80.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Jersey,
        fontSize = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Jersey,
        fontSize = 12.sp
    )

)