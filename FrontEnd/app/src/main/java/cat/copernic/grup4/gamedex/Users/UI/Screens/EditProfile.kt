package cat.copernic.grup4.gamedex.Users.UI.Screens

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavController
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import cat.copernic.grup4.gamedex.Users.UI.ViewModel.UserViewModel

@Composable
fun EditProfileScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val currentUser by userViewModel.currentUser.collectAsState()
    var username by remember { mutableStateOf(currentUser?.username ?: "") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2E6FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clickable { imagePickerLauncher
                    .launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts
                                .PickVisualMedia.ImageOnly
                        )
                    ) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(profileImageUri ?: currentUser?.profileImageUrl),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = username,
            onValueChange = { username = it },
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier
                .background(Color.White, MaterialTheme.shapes.medium)
                .padding(12.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                userViewModel.updateUser(username, profileImageUri, context)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8A56AC))
        ) {
            Text("Save Changes", color = Color.White)
        }
    }
}
