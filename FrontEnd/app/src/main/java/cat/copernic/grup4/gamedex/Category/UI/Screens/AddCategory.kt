package cat.copernic.grup4.gamedex.Category.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.grup4.gamedex.Core.ui.theme.BottomNavBar
import cat.copernic.grup4.gamedex.Core.ui.theme.TopBar
import cat.copernic.grup4.gamedex.R

@Composable
fun AddCategoryScreen() {
    var categoryName by remember { mutableStateOf("") }
    var categoryDescription by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.background))
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(onLogoutClick = {}, profileImageRes = R.drawable.coche)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.add_category),
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(1f)
                    .padding(bottom = 100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(label = "Name", text = categoryName) { categoryName = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(label = "Description", text = categoryDescription, height = 100.dp) { categoryDescription = it }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Image", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(20.dp))
                    ImageUploadSection()
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    //TODO añadir acción de registro
                    onClick = { /* Acción de registro */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),

                    ) {
                    Text(text = stringResource(id = R.string.confirm), color = Color.White,  fontWeight = FontWeight.Bold)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavBar(onItemSelected = {})
        }
    }
}

@Composable
fun TextField(label: String, text: String, height: Dp = 56.dp, onTextChanged: (String) -> Unit) {
    Column {
        Text(label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        )
    }
}

@Composable
fun ImageUploadSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = painterResource(id = R.drawable.coche),
            modifier = Modifier .fillMaxSize(),
            contentDescription = "My Image"
        )
        IconButton(
            onClick = { /* Open image picker */ },
            modifier = Modifier
                .size(48.dp)
                .background(colorResource(R.color.header), shape = RoundedCornerShape(50))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_category),
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddCategoryScreen() {
    AddCategoryScreen()
}
