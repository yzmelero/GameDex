package cat.copernic.grup4.gamedex.Library.UI.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cat.copernic.grup4.gamedex.Core.Model.StateType
import cat.copernic.grup4.gamedex.Core.ui.theme.GameDexTypography
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.videogames.ui.screen.ConfirmButton
import cat.copernic.grup4.gamedex.videogames.ui.screen.HeaderSection


@Composable
fun AddGameToLibraryScreen() {

    val context = LocalContext.current
    var selectedState by remember { mutableStateOf(context.getString(R.string.select)) }
    val stateOptions = listOf(
        context.getString(R.string.finished),
        context.getString(R.string.playing),
        context.getString(R.string.wanttoplay),
        context.getString(R.string.dropped)
    )

    var rating by remember { mutableDoubleStateOf(0.0) }
    var comment by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()

        Spacer(modifier = Modifier.height(16.dp))
        //TODO Canviar la lletra
        Text(text = context.getString(R.string.review), style = GameDexTypography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clickable { expanded = true }
                .padding(12.dp)
        ) {
            Text(text = selectedState)

            DropdownMenu(
                expanded = expanded,
                modifier = Modifier.zIndex(1f),
                onDismissRequest = { expanded = false }
            ) {
                stateOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            selectedState = option
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Rating
            //TODO Canviar lletra
            Text(text = context.getString(R.string.rating), fontSize = 18.sp)
            Row {
                (1..10).forEach { index ->
                    Icon(
                        imageVector = if (index <= rating.toInt()) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = "Star $index",
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { rating = index.toDouble() }

                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Comentari
            //TODO Canviar lletra
            Text(text = context.getString(R.string.comment), fontSize = 18.sp)

            BasicTextField(
                value = comment,
                onValueChange = { comment = it },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .heightIn(min = 100.dp)
                    .border(
                        1.dp,
                        colorResource(id = R.color.boxbackground),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                textStyle = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //TODO API afegir videojoc a la biblioteca
            Button(
                onClick = { /* Acción de afegir ressenya */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(text = stringResource(R.string.confirm), color = Color.White)
            }
        }
    }
}

@Composable
@Preview
fun AddGameToLibraryScreenPreview() {
    AddGameToLibraryScreen()
}