package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.copernic.grup4.gamedex.R

@Composable
fun SignUpScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC0CB)), // Fondo degradado rosa-morado
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "GDEX", fontSize = 24.sp, color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Sign Up", fontSize = 22.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputField(label = "Username", value = username) { username = it }
                InputField(label = "Password", value = password, isPassword = true) { password = it }
                InputField(label = "Name", value = name) { name = it }
                InputField(label = "Surname", value = surname) { surname = it }
                InputField(label = "Email", value = email) { email = it }
                InputField(label = "Telephone", value = telephone, keyboardType = KeyboardType.Number) { telephone = it }
                InputField(label = "BirthDate", value = birthDate) { birthDate = it }

                Spacer(modifier = Modifier.height(10.dp))

                AvatarSection()

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Acción de registro */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Text(text = "Confirm", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun InputField(label: String, value: String, isPassword: Boolean = false, keyboardType: KeyboardType = KeyboardType.Text, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, color = Color.Black, fontSize = 14.sp)
        TextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun AvatarSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.coche),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50))
                .clickable { /* Acción para elegir imagen */ }
        )
        Text(text = "Avatar", fontSize = 14.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen()
}
